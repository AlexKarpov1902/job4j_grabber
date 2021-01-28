package ru.job4j.html;

import static org.hamcrest.Matchers.is;

import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;


public class StringToDateTest {

    @Test
    public void whenStringToDate() {
        String dateStr ="22 янв 21, 16:18";
        Date date = StringToDate.parse(dateStr);
        Date dateExp = new Date(121, 0,22,16,18);
        assertThat(date, is(dateExp));
    }

    @Test
    public void whenWrongStringToDate() {
        String dateStr ="22 янв. 21, 16:18";
        Date date = StringToDate.parse(dateStr);
        Date dateExp = new Date(70, 0,1,5,0);
     //   System.out.println("dateExp = " + dateExp);
        assertThat(date, is(dateExp));


    }

}