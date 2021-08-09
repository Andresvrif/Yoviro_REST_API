package com.yoviro.rest.util;

import com.yoviro.rest.config.AppConfig;

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
}