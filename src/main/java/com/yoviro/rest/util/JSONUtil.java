package com.yoviro.rest.util;

import java.util.HashMap;
import java.util.Map;

public class JSONUtil {

    public static Map<String, Object> addRootName(Object object) {
        var mapper = new HashMap<String, Object>();
        mapper.put(object.getClass().getSimpleName(), object);
        return mapper;
    }
}
