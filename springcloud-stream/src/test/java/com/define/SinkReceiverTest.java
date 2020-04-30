package com.define;

import com.define.sender.SinkSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * xxx
 *
 * @author linyincheng
 * @date 2020/4/1 16:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringcloudStreamApplication.class)
@WebAppConfiguration
public class SinkReceiverTest {

    @Autowired
    private SinkSender sinkSender;

    @Test
    public void contextLoads() {
        sinkSender.output().send(MessageBuilder.withPayload("From SinkSender").build());
    }
}
