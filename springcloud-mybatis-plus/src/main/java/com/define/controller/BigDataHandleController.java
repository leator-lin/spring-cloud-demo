package com.define.controller;

import com.define.entity.TUser;
import com.define.service.BigDataHandleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/bigData")
public class BigDataHandleController {

    @Resource
    private BigDataHandleService bigDataHandleService;

    @PostMapping("saveData")
    public void saveData(@RequestBody TUser tUser) {
        bigDataHandleService.saveData(tUser);
    }

    @GetMapping("testSaveData")
    public void testSaveData() {
        TUser tUser = new TUser();
        tUser.setName("林银城");
        tUser.setAge(28);
        tUser.setEmail("c1053595207@163.com");
        tUser.setCreateTime(LocalDateTime.now());
        tUser.setUpdateTime(LocalDateTime.now());
        tUser.setVersion(1);
        for(int i = 1; i <= 4000; i++) {
            tUser.setId(i);
            bigDataHandleService.saveData(tUser);
        }
    }

}
