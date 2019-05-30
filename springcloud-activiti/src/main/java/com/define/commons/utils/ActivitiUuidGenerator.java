package com.define.commons.utils;

import org.activiti.engine.impl.cfg.IdGenerator;

import java.util.UUID;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/5/30 10:18
 */
public class ActivitiUuidGenerator implements IdGenerator {

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Activiti ID 生成
     */
    @Override
    public String getNextId() {
        return ActivitiUuidGenerator.uuid();
    }
}
