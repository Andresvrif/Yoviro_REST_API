package com.yoviro.rest.util;

import com.yoviro.rest.config.AppConfig;
import org.springframework.data.domain.Page;
import java.util.HashMap;
import java.util.Map;

public class JSONUtil {

    /***
     * Author : Andrés V.
     * Desc : Adds root node based in the class name
     * @param object
     * @return
     */
    public static Map<String, Object> addRootName(Object object) {
        var mapper = new HashMap<String, Object>();
        mapper.put(object.getClass().getSimpleName(), object);
        return mapper;
    }

    public static Map<String, Object> addRootName(String rootName, HashMap<String, Object> data) {
        var map = new HashMap<String, Object>();
        map.put(rootName, data);
        return map;
    }

    public static Map<String, Object> pageToJson(Page page){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(AppConfig.PAGE_RESPONSE_TOTAL_PAGES_NAME, page.getTotalPages());
        map.put(AppConfig.PAGE_RESPONSE_CURRENT_PAGE, page.getNumber() + 1);
        map.put(AppConfig.PAGE_RESPONSE_TOTAL_ELEMENTS, page.getTotalElements());
        
        return map;
    }
}