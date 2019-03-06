package com.define.controller;

import com.define.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/createUser")
    public String createUser(Integer id,String name){
        userService.createUser(id,name);
       return "success";
    }

    @GetMapping("/addField")
    public String addField() {
        String fieldName = "name";
        userService.addField(fieldName);
        return "success";
    }

    @GetMapping("/batchCreateUser")
    public String batchCreateUser() {
        userService.batchCreateUser();
        return "success";
    }
}