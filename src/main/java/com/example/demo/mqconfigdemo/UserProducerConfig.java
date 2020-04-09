package com.example.demo.mqconfigdemo;

import com.alibaba.fastjson.JSON;
import com.example.demo.mqconfig.DefaultMQProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UserProducerConfig {


    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String nameserAddr;

    @Value("${rocketmq.producer.instanceName}")
    private String instanceName;

    @Value("${rocketmq.producer.maxMessageSize}")
    private int maxMessageSize;

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private int sendMsgTimeout;


    @Bean
    public DefaultMQProducer getDefaultProducer() {

        DefaultMQProducer defaultMQProducer = DefaultMQProducerConfig.getRocketMQProducer(nameserAddr,groupName,instanceName, maxMessageSize, sendMsgTimeout);
        System.out.println("================>生产者创建完成<================" + JSON.toJSONString(defaultMQProducer));

        return defaultMQProducer;

    }

}
