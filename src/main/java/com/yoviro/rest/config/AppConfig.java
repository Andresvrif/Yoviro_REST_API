package com.yoviro.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AppConfig {

    public static Integer EFFECTIVE_DATE = null;
    public static Integer PAGE_SIZE = null;
    public static String PAGE_REQUEST_PARAM_NAME = null;
    public static String PAGE_RESPONSE_TOTAL_PAGES_NAME = null;
    public static String PAGE_RESPONSE_CURRENT_PAGE = null;
    public static String PAGE_RESPONSE_TOTAL_ELEMENTS = null;
    public static String DATE_FORMAT = null;
    public static String DATE_TIME_FORMAT = null;
    public static String TIME_FORMAT = null;

    @Value("${effectivedate.hour}")
    public String _effectiveDateHour;

    @Value("${page.size}")
    public String _pageSize;

    @Value("${page.request.param.name}")
    public String _pageRequestParamName;

    @Value("${page.response.totalPages.name}")
    public String _pageResponseTotalPages;

    @Value("${page.response.currentPage.name}")
    public String _pageResponseCurrentPage;

    @Value("${page.response.totalElements.name}")
    public String _pageResponseTotalElements;

    @Value("${spring.mvc.format.date}")
    public String _dateFormat;

    @Value("${spring.mvc.format.date-time}")
    public String _dateTimeFormat;

    @Value("${spring.mvc.format.time}")
    public String _timeFormat;

    @Transactional(readOnly = true)
    @EventListener(ApplicationReadyEvent.class)
    public void afterBoot() {
        //TODO realizar lógica despues del boteo
        AppConfig.EFFECTIVE_DATE = Integer.parseInt(_effectiveDateHour);
        AppConfig.PAGE_SIZE = Integer.parseInt(_pageSize);
        AppConfig.PAGE_REQUEST_PARAM_NAME = _pageRequestParamName;
        AppConfig.PAGE_RESPONSE_TOTAL_PAGES_NAME = _pageResponseTotalPages;
        AppConfig.PAGE_RESPONSE_CURRENT_PAGE = _pageResponseCurrentPage;
        AppConfig.PAGE_RESPONSE_TOTAL_ELEMENTS = _pageResponseTotalElements;
        AppConfig.DATE_FORMAT = _dateFormat;
        AppConfig.DATE_TIME_FORMAT = _dateTimeFormat;
        AppConfig.TIME_FORMAT = _timeFormat;
    }
}