package com.example.demo.consumer;

import com.example.demo.message.MessageListen;
import com.example.demo.message.impl.AnimalMessageProcessorImpl;
import com.example.demo.message.impl.UserMessageProcessorImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/17 16:27
 * Description：消费者配置
 */
@Configuration
@Slf4j
public abstract class RocketMQConsumer {

    @Autowired
    private MessageListen messageListen;

    @Autowired
    private UserMessageProcessorImpl userMessageProcessor;

    @Autowired
    private AnimalMessageProcessorImpl animalMessageProcessor;

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
/*

    @Value("${rocketmq.consumer.topic.topic}")
    private String topic2;

    @Value("${rocketmq.consumer.tag.tag}")
    private String tag2;
*/

    /**
     * 如果只想消费topic下的某几个tag，以用 “||”隔开。比如：consumer.subscribe("topic2020", "Tag1 || Tag2");
     * 如果想消费topic下所有的tag，用“*”。比如：consumer.subscribe("topic2020", "*");
     */
//    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer getRocketMQConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);

/*        messageListen.registerHandler(tag, userMessageProcessor);
        messageListen.registerHandler(tag2, animalMessageProcessor);
        consumer.registerMessageListener(messageListen);*/
        try {
            consumer.subscribe(topic, tag);
//            consumer.subscribe(topic2, "*");

            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeOrderlyContext) -> {
                try {
                    for (MessageExt messageExt : list) {
                        String restag = messageExt.getTags();
                        String restopic = messageExt.getTopic();
                        log.info(String.format("mq收到消息，来自标签%s", restag));
                        System.out.println(restag);
                        System.out.println(restopic);
                        System.out.println("消费成功");
                        consume(restag, restopic, messageExt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //稍后再试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                //消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

            log.info("===============>消费者创建完成，ConsumerGroupName:{}<==============", groupName);
            log.info("=====>消费者监听开始,groupName:{},topic:{},namesrvAddr:{}<=======", groupName, topic, namesrvAddr);
        } catch (MQClientException e) {
            log.error("======>消费者监听失败,groupName:{},topic:{},namesrvAddr:{}<======", groupName, topic, namesrvAddr, e);
            e.printStackTrace();
        }
        return consumer;
    }

    protected abstract void consume(String tag, String topic, MessageExt messageExt);

}
