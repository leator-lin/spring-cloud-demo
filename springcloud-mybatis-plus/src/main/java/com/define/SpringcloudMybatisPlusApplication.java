package com.define;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用mybatis-plus的步骤：
 * 1，导入mybatis-plus的依赖；
 * 2，在主类上加上注解@MapperScan；
 */
@SpringBootApplication
@MapperScan("com.define.mapper")
public class SpringcloudMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudMybatisPlusApplication.class, args);
    }

}
