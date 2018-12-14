package com.define.commons.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 整合thymeleaf后访问静态页面my_thymeleaf.html的控制器
 * 注意：注入的时候一定要是Controller 不要是RestController 因为它是rest接口（json格式） 是解析不到html的
 *
 * @Author: Lea
 * @Date: 2018/11/27 16:03
 */
@RestController
@RequestMapping(value="/")
public class ThymeleafController {

    @GetMapping(value = "/home")
    public ModelAndView homePage(){
        return new ModelAndView("my_thymeleaf.html");
    }

    /*public String homePage(){
        return "my_thymeleaf.html";
    }*/
}
