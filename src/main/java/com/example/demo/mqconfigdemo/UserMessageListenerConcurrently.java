package com.example.demo.mqconfigdemo;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserMessageListenerConcurrently implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        for (MessageExt messageExt : msgs) {

            try {
                String messageBody = new String(messageExt.getBody());
                System.out.println("user测试消息体：" + messageBody);
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        // 如果没有return success，consumer会重复消费此信息，直到success。

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }


}
