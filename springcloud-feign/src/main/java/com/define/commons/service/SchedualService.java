package com.define.commons.service;

import com.define.commons.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "client-service")
public interface SchedualService {

    @GetMapping("/feign")
    R feignTest(@RequestParam(value="name") String name);
}