package com.yoviro.rest.util;

public class PageUtil {
    public static Integer definePageNumber(String pageParam) {
        pageParam = (pageParam == null || pageParam.compareToIgnoreCase("0") == 0) ? "1" : pageParam;
        return Integer.parseInt(pageParam) - 1;
    }
}
