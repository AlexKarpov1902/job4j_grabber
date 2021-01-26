package ru.job4j.html;

import java.nio.file.Files;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {

    private static final String[] SHORT_MONTHS = new String[]{
            "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
    private static final String PATTERN_DATE = "dd MMM yy, HH:mm";

    public static Date parse(String stringDate) {
        DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance();
        dateFormatSymbols.setShortMonths(SHORT_MONTHS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DATE, dateFormatSymbols);
        Date date = null;
        try {
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            try {
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
                    date = new Date(0L);
                }
            } catch (ParseException e1) {
                System.out.println(e1.getMessage() + "-" + e1.getErrorOffset());
                date = new Date(0L);
            }
        }
        return date;
    }
}
