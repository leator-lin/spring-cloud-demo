package com.define.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库添加表字段
 *
 * @Author: 自己的中文或英文名
 * @Date: 2019/1/13 0:25
 */
public class AddFieldUtil {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() {
        String sql = "";
        for(int i = 0; i < 300; i++) {
//            sql = "ALTER table user ADD name" + i + " VARCHAR(255);";
            sql = "select * from user";
            System.out.println(sql);
            jdbcTemplate.execute(sql);
        }
    }
}
