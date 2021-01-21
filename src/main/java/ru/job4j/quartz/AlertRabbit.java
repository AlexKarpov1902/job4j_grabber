package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Properties cfg = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader().
                getResourceAsStream("rabbit.properties")) {
            cfg.load(in);
        }
        Class.forName(cfg.getProperty("driver-class-name"));
        try (Connection connection = DriverManager.getConnection(
                cfg.getProperty("url"),
                cfg.getProperty("username"),
                cfg.getProperty("password")))  {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();

        } catch (SchedulerException | InterruptedException | SQLException se) {
            se.printStackTrace();
        }

    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context)  {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            //store.add(System.currentTimeMillis());
            try (PreparedStatement statement =
                         cn.prepareStatement("insert into rabbit (created_date) values (?)")) {
                statement.setLong(1, System.currentTimeMillis());
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}