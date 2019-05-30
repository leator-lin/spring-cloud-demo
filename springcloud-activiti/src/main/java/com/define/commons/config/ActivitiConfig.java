package com.define.commons.config;

import com.define.commons.utils.ActivitiUuidGenerator;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * xxx
 *
 * @Author: linyincheng
 * @Date: 2019/5/30 10:16
 */
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        springProcessEngineConfiguration.setIdGenerator(new ActivitiUuidGenerator());
    }
}
