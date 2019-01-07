package com.define.commons.utils;

/**
 * 流程变量枚举
 *
 * @Author: Lea
 * @Date: 2019/1/4 15:02
 */
public enum TypeEnum {
    //同意办理
    AGREE(0),
    //不同意办理
    REJECT(1),
    //回退
    RECALL(2);

    private int value;

    private TypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
