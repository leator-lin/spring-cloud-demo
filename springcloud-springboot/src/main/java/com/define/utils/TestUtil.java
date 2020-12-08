package com.define.utils;

import org.apache.commons.lang3.RandomUtils;

public class TestUtil {
    public static void main(String[] args) {
        for(int i = 0; i < 1000000; i++) {
            System.out.println((float) Math.round((RandomUtils.nextFloat(0f, 0.8f) - 0.4f) * 1000) / 1000);
//            System.out.println((float) Math.round((RandomUtils.nextFloat(0f, 1.7f) - 0.85f) * 1000) / 1000);
        }
    }
}
