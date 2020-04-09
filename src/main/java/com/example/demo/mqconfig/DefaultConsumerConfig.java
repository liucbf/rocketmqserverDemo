package com.example.demo.mqconfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

@Slf4j
public class DefaultConsumerConfig {


    private DefaultMQPushConsumer consumer;

    public DefaultConsumerConfig(String namesrvAddr, String consumerGroup, String topic, String tags, MessageListenerConcurrently messageListenerConcurrently) {

        try {
            consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setConsumeThreadMin(10);
            consumer.setConsumeThreadMax(50);
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.subscribe(topic, tags);
            consumer.setConsumeTimeout(15000);
            consumer.registerMessageListener(messageListenerConcurrently);
            // 启动消费者
            consumer.start();

            log.info("消费者：{}--->启动成功", consumerGroup);
        } catch (MQClientException e) {
            log.error("消费者：{}--->启动失败", consumerGroup);
            log.error("启动失败的原因", e);
        }
    }

    public DefaultMQPushConsumer getDefaultConsumer() {
        return consumer;
    }
}
