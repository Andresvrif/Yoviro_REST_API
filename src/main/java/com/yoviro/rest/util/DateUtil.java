package com.yoviro.rest.util;

import com.yoviro.rest.config.AppConfig;
import org.joda.time.DateTimeComparator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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

    public static Boolean insideOfRange(Date dateToEvaluate,
                                        Date startDate,
                                        Date endDate,
                                        Integer dayFrequency) {
        if (compareIgnoreTime(dateToEvaluate, startDate) < 0) return Boolean.FALSE;
        if (endDate != null && compareIgnoreTime(dateToEvaluate, endDate) > 0) return Boolean.FALSE;
        if (dayFrequency == 1) return Boolean.TRUE; //If has a daily frequency, there isn't a necessity to be evaluated

        Calendar dateToEvaluateCalendar = dateToCalendar(dateToEvaluate);
        Calendar indexDateCalendar = dateToCalendar(startDate);

        while (compareIgnoreTime(indexDateCalendar.getTime(), dateToEvaluateCalendar.getTime()) <= 0) {
            if (compareIgnoreTime(indexDateCalendar.getTime(), dateToEvaluateCalendar.getTime()) == 0) {
                return Boolean.TRUE;
            }
            indexDateCalendar.add(Calendar.DATE, dayFrequency);
        }

        return Boolean.FALSE;
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }
}