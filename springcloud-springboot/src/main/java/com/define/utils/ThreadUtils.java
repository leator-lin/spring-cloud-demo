package com.define.utils;

import java.util.*;

public class ThreadUtils {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("离离");
        list.add("离离");
        list.add("离离");
        list.add("别别");
        list.add("汉汉");
        list.add("城城");
        Set<String> set = new HashSet<>();
        set.addAll(list);
        System.out.println(set);
    }
}
