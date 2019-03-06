package com.define.commons.controller;

import com.define.commons.service.SchedualService;
import com.define.commons.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * xxx
 *
 * @Author: 自己的中文或英文名
 * @Date: 2018/12/17 22:19
 */
@RestController
public class FeignController {
    //编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
    @Autowired
    SchedualService schedualService;

    @GetMapping(value = "/hi")
    public R feignTest() {
        R r = schedualService.feignTest();
        return r;
    }
}
