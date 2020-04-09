package com.example.demo.mqconfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/17 16:21
 * Description：生产者配置
 */
@Slf4j
public class DefaultMQProducerConfig {

    public static DefaultMQProducer getRocketMQProducer(String namesrvAddr, String groupName, String instanceName, int maxMessageSize, int sendMsgTimeout) {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("================>生产者创建失败，ProducerGroupName{}<================", groupName);
        }
        log.info("================>生产者创建完成，ProducerGroupName{}<================", groupName);
        return producer;
    }

}
