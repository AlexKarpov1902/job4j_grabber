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

    private final String[] shortMonths = new String[]{
            "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
    private SimpleDateFormat simpleDateFormat;
    private DateFormatSymbols dateFormatSymbols;

    public SqlRuParse() {
        this.dateFormatSymbols = DateFormatSymbols.getInstance();
        dateFormatSymbols.setShortMonths(shortMonths);
        this.simpleDateFormat = new SimpleDateFormat("dd MMM yy, HH:mm", dateFormatSymbols);
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
            Date date = getDateFromString(txtDate);
            System.out.println("дата: " + date);
        }
    }

    private Date getDateFromString(String stringDate) throws ParseException {
        Date date = null;
        try {
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            if (stringDate.startsWith("сегодня")) {
                String newDate = simpleDateFormat.format(new Date());
                String s = newDate.substring(0, 11) + stringDate.substring(9);
                date = simpleDateFormat.parse(s);
            } else if (stringDate.startsWith("вчера")) {
                String newDate = simpleDateFormat.format(new Date(System.currentTimeMillis() - 86_400_000L));
                String s = newDate.substring(0, 11) + stringDate.substring(7);
                date = simpleDateFormat.parse(s);
            } else {
                System.out.println(e.getMessage() + "-" + e.getErrorOffset());
            }
        }
        return date;
    }


    public Post detail(String link) throws IOException, ParseException {

        Document doc = Jsoup.connect(link).get();
        String text = doc.select("td.msgBody").get(1).text();
        System.out.println("text = " + text);   
        String dateString = doc.select("td.msgFooter").get(1).text().substring(0, 16);
        Date date = getDateFromString(dateString);
        return new Post(link, text, date);
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

