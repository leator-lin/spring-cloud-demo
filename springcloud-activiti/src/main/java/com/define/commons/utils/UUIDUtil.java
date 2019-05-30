package com.define.commons.utils;

import java.util.UUID;

/**
 * 生成UUID的工具类
 *
 * @Author: linyincheng
 * @Date: 2019/4/17 11:15
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
