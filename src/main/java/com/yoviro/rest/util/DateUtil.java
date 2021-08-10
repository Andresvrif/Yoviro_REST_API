package com.yoviro.rest.util;

import com.yoviro.rest.config.AppConfig;
import org.joda.time.DateTimeComparator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date instanceDate(String date) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(AppConfig.DATE_FORMAT);
            return sdf.parse(date);
        } catch (Exception ex) {
            throw new Exception("Error al transformar la fecha : " + date);
        }
    }

    public static int compareIgnoreTime(Date date1, Date date2) {
        return DateTimeComparator.getDateOnlyInstance().compare(date1, date2);
    }
}