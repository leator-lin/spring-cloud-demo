package com.define.sender;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * xxx
 *
 * @author linyincheng
 * @date 2020/4/1 16:25
 */
public interface SinkSender {

    @Output(Source.OUTPUT)
    MessageChannel output();
}
