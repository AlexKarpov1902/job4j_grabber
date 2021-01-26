package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SqlRuParse {

    public SqlRuParse() {
    }

    public void parse(String link) throws IOException, ParseException {

        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Elements sibling = td.siblingElements();
            String txtDate = sibling.get(4).text();
            Date date = StringToDate.parse(txtDate);
            System.out.println("дата: " + date);
        }
    }

    public Post detail(String link) throws IOException, ParseException {

        Document doc = Jsoup.connect(link).get();
        String text = doc.select("td.msgBody").get(1).text();
        System.out.println("text = " + text);   
        String dateString = doc.select("td.msgFooter").get(1).text().substring(0, 16);
        Date date = StringToDate.parse(dateString);
        String name = doc.select("td.messageHeader").get(1).text();
        return new Post(name, link, text, date);
    }

    public static void main(String[] args) throws IOException, ParseException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String link = "https://www.sql.ru/forum/job-offers/";

        for (int i = 1; i < 6; i++) {
            link = link.concat(i == 1 ? "" : String.valueOf(i));
            System.out.printf(" ------------------------- Страница %d -------------------------------%s", i, System.lineSeparator());
            sqlRuParse.parse(link);
        }
        String link1 =
                "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        Post post = sqlRuParse.detail(link1);
        System.out.println("post = " + post);
    }
}

