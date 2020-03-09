package com.define.service.impl;

import com.define.dto.User;
import com.define.service.SchedualService;
import org.springframework.stereotype.Component;

@Component
public class SchedualServiceImpl implements SchedualService {
    @Override
    public String feignTest(String name) {
        return "error";
    }

    @Override
    public User feignTest1(String name, Integer age) {
        return new User("未知", 0);
    }

    @Override
    public String feignTest2(User user) {
        return "error";
    }
}
