package com.define.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("林", "银城");
        map.put(null, null);
        System.out.println(map);
    }
}
