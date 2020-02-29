package com.define.service;

import com.define.config.DisableHystrixConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

//configuration是用来指定服务客户端关闭Hystrix支持的
@FeignClient(value = "eurekaClient-service", configuration = DisableHystrixConfiguration.class)
public interface RefactorSchedualService extends RefactorService {

}