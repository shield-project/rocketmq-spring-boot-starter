package org.shoper.test;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

/**
 * @author ShawnShoper
 * @date 2018/7/30 11:43
 */
@RestController
public class ProducerController {
    @Autowired
    MQProducer mqProducer;

    @GetMapping("/pro")
    public String msg(String content) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        mqProducer.send(new Message("test", "test", content.getBytes(Charset.forName("UTF-8"))));
        return content;
    }
}
