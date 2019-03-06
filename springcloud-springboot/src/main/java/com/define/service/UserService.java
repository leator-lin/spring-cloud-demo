package com.define.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void createUser(Integer id,String name){
        System.out.println("createUser");
        jdbcTemplate.update("insert into user values(?,?);",id,name);
        System.out.println("用户添加成功！！");
    }

    public void addField(String fieldName) {
        String sql = "";
        System.out.println("addField");
        for(int i = 0; i < 200; i++) {
            sql = "alter table user add " + fieldName + i + " varchar(40)";
            jdbcTemplate.execute(sql);
        }
    }

    public void batchCreateUser() {
        String sql = "";
        String name = "";
        System.out.println("addField");
        for(int i = 0; i < 200000; i++) {
            name = "name" + i;
            jdbcTemplate.update("insert into user values(?,?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?,\n" +
                    "?,?);\n",i,name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name,
                    name);
            System.out.println("用户添加成功！！");
        }
    }

}