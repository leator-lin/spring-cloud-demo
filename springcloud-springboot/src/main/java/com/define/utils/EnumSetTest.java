package com.define.utils;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;

enum Season {
    SPRING, SUMMER, FALL, WINTER
}

public class EnumSetTest {
    public static void main(String[] args) {
        //创建一个EnumSet集合，集合元素就是Season枚举类的全部枚举值
        EnumSet es1 = EnumSet.allOf(Season.class);
        //输出[SPRING, SUMMER, FALL, WINTER]
        System.out.println(es1);
        //创建一个EnumSet空集合，指定其集合元素是Season类的枚举值
        EnumSet es2 = EnumSet.noneOf(Season.class);
        //输出[]
        System.out.println(es2);
        //手动添加两个元素
        es2.add(Season.WINTER);
        es2.add(Season.SPRING);
        //输出[Spring, WINTER]
        System.out.println(es2);
        //以指定枚举值创建EnumSet集合
        EnumSet es3 = EnumSet.of(Season.SUMMER, Season.WINTER);
        //输出[SUMMER, WINTER]
        System.out.println(es3);//创建一个包含两个枚举值范围内所有枚举值的EnumSet集合
        EnumSet es4 = EnumSet.range(Season.SUMMER, Season.WINTER);
        //输出[SUMMER, FALL, WINTER]
        System.out.println(es4);
        //新创建的EnumSet集合元素和es4集合元素有相同的类型
        //es5集合元素 + es4集合元素=Season枚举类的全部枚举值
        EnumSet es5 = EnumSet.complementOf(es4);
        System.out.println(es5);
        //创建一个集合
        Collection c = new HashSet();
        c.clear();
        c.add(Season.SPRING);
        c.add(Season.WINTER);
        //复制Collection集合中的所有元素来创建EnumSet集合
        EnumSet es = EnumSet.copyOf(c);
        //输出es
        System.out.println(es);
        c.add("111");
        c.add("222");
        //下面代码出现异常，因为c集合里的元素不是全部都为枚举值
        es = EnumSet.copyOf(c);
    }
}