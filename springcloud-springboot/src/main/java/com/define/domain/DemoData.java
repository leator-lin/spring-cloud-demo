package com.define.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DemoData {
    @ExcelProperty("呼出时间")
    private Date date;
    @ExcelProperty("呼出号码")
    private String phone;
    @ExcelProperty("通话轮次")
    private int round;
    @ExcelProperty("通话时长")
    private String time;
    @ExcelProperty("通话序号")
    private String number;
    @ExcelProperty("意向信息")
    private String msg;
    @ExcelProperty("通话录音")
    private String record;
    @ExcelProperty("通话文本")
    private String text;

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
