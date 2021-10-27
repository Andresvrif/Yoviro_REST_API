package com.yoviro.rest.util;

import com.yoviro.rest.config.AppConfig;
import org.apache.tomcat.jni.Local;
import org.joda.time.DateTimeComparator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

    public static int compareIgnoreTime(LocalDateTime date1,
                                        LocalDateTime date2) {
        return date1.toLocalDate().compareTo(date2.toLocalDate());
    }

    public static int compareIgnoreTime(LocalDate date1,
                                        LocalDate date2) {
        var x = date1.compareTo(date2);
        return x;
    }

    public static Boolean insideOfRange(LocalDateTime dateToEvaluate,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        Integer dayFrequency) {
        if (compareIgnoreTime(dateToEvaluate.toLocalDate(), startDate) < 0) return Boolean.FALSE;
        if (endDate != null && compareIgnoreTime(dateToEvaluate.toLocalDate(), endDate) > 0) return Boolean.FALSE;
        if (dayFrequency == 1) return Boolean.TRUE; //If has a daily frequency, there isn't a necessity to be evaluated

        LocalDate indexLocalDate = startDate;
        while (compareIgnoreTime(indexLocalDate, dateToEvaluate.toLocalDate()) <= 0) {
            if (compareIgnoreTime(indexLocalDate, dateToEvaluate.toLocalDate()) == 0) {
                return Boolean.TRUE;
            }
            indexLocalDate = indexLocalDate.plusDays(dayFrequency);
        }

        return Boolean.FALSE;
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    public static LocalDate instanceLocalDate(String str, DateTimeFormatter format) {
        TemporalAccessor temp = format.parse(str);
        return LocalDate.from(temp);
    }

    public static LocalDateTime instanceEffectiveDateTime(LocalDate referenceDate) {
        LocalTime localTime = LocalTime.of(AppConfig.EFFECTIVE_TIME, 0);
        return LocalDateTime.of(referenceDate, localTime);
    }

    public static LocalDateTime test(LocalDate referenceDate) {
        LocalTime localTime = LocalTime.of(AppConfig.EFFECTIVE_TIME, 0);
        return LocalDateTime.of(referenceDate, localTime);
    }
}