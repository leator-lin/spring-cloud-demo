package com.define.commons.service;

import com.define.commons.conf.FeignConfig;
import com.define.commons.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "client-service", configuration = FeignConfig.class)
public interface SchedualService {
//    @RequestMapping(value = "/feign",method = RequestMethod.GET)
    @GetMapping("/feign")
    R feignTest();
}