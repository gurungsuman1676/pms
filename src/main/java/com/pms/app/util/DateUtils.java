package com.pms.app.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by arjun on 6/23/2017.
 */
public class DateUtils {
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}