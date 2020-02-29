package com.define.controller;

import com.define.dto.User;
import com.define.service.RefactorSchedualService;
import com.define.service.SchedualService;
import com.define.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RefactorFeignController {
    //编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
    @Autowired
    RefactorSchedualService refactorSchedualService;

    @GetMapping(value = "/feignTest3")
    public R feignTest3() {
        StringBuilder sb = new StringBuilder();
        sb.append(refactorSchedualService.feignTest4("林银城", 27)).append("\n");
        sb.append(refactorSchedualService.feignTest5(new User("林银城", 27))).append("\n");
        return R.ok(sb.toString());
    }
}
