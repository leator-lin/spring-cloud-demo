package com.define.controller;

import com.define.dto.User;
import com.define.service.SchedualService;
import com.define.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * xxx
 *
 * @Author: Lea
 * @Date: 2018/12/17 22:19
 */
@RestController
public class FeignController {
    //编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
    @Autowired
    SchedualService schedualService;

    @GetMapping(value = "/feignTest1")
    public R feignTest1(String name) {
        String result = schedualService.feignTest(name);
        return R.ok(result);
    }

    @GetMapping(value = "/feignTest2")
    public R feignTest2() {
        StringBuilder sb = new StringBuilder();
        sb.append(schedualService.feignTest1("林银城", 27)).append("\n");
        sb.append(schedualService.feignTest2(new User("林银城", 27))).append("\n");
        return R.ok(sb.toString());
    }
}
