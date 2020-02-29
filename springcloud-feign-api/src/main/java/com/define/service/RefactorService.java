package com.define.service;

import com.define.dto.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refactor")
public interface RefactorService {

    @GetMapping("/feign3")
    String feignTest3(@RequestParam("name") String name);

    @GetMapping("/feign4")
    User feignTest4(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @PostMapping("/feign5")
    String feignTest5(@RequestBody User user);
}