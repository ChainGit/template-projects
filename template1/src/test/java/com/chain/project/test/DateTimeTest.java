package com.chain.project.test;

import com.chain.project.common.utils.JyComUtils;
import org.junit.Test;

import java.util.Date;

public class DateTimeTest {

    @Test
    public void test1() {
        String time = "2017-08-01 11:01:22";
        Date date = JyComUtils.parseDateFormString(time);
        System.out.println(date);
        System.out.println(date.getTime());//1501556482000
    }

    @Test
    public void test2() {
        long time = 1501556482000L;
        Date date = JyComUtils.convertLongToDate(time);
        String dateString = JyComUtils.toFormatDateString(date);
        System.out.println(dateString);
    }
}
