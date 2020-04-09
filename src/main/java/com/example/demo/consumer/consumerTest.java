package com.example.demo.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class consumerTest extends  RocketMQConsumer {
/*

    @Value("${rocketmq.consumer.tag}")
    private String tag;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

*/

/*
    @Override
    public DefaultMQPushConsumer getRocketMQConsumer( topic tag) {
        return super.getRocketMQConsumer(this.topic,this.tag);
    }
*/



    @Override
    protected void consume(String tag, String topic, MessageExt messageExt) {
        System.out.println("消费者测试tag："+tag);
        System.out.println("消费者测试topic："+topic);
        String messageBody = new String(messageExt.getBody());
        System.out.println("消费者测试消息体："+messageBody);
    }
}
