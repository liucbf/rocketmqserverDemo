package com.example.demo.mqconfigdemo;

import com.alibaba.fastjson.JSON;
import com.example.demo.mqconfig.DefaultConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConsumerConfig {

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Value("${rocketmq.consumer.tag}")
    private String tag;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;



    @Bean
    public DefaultConsumerConfig DefaultConsumer() {
        DefaultConsumerConfig consumer = new DefaultConsumerConfig(namesrvAddr, groupName, topic, tag,new UserMessageListenerConcurrently() );
        System.out.println("================>user消费者创建完成<================"+ JSON.toJSONString(consumer));
        return consumer;
    }



}
