package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class SqlRuParse {
    public static void main(String[] args) throws IOException, ParseException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        DateFormatSymbols dfs = DateFormatSymbols.getInstance();
        dfs.setShortMonths(new String[]{
                "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"});
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy, HH:mm", dfs);

        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Elements sibling = td.siblingElements();
            String txtDate = sibling.get(4).text();
            Date date = null;
            try {
                date = sdf.parse(txtDate);
            } catch (ParseException e) {
                if (txtDate.startsWith("сегодня")) {
                    String newDate = sdf.format(new Date());
                    String s = newDate.substring(0, 11) + txtDate.substring(9);
                    date = sdf.parse(s);
                } else if (txtDate.startsWith("вчера")) {
                    String newDate = sdf.format(new Date(System.currentTimeMillis() - 86_400_000L));
                    String s = newDate.substring(0, 11) + txtDate.substring(7);
                    date = sdf.parse(s);
                } else {
                    System.out.println(e.getMessage() + "-" + e.getErrorOffset());
                }
             }
            System.out.println("дата: " + date);
        }
    }
}

