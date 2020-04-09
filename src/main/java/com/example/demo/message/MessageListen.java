package com.example.demo.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/17 16:26
 * Description：监听类
 */
@Component
@Slf4j
public class MessageListen implements MessageListenerConcurrently {

    private Map<String, MessageProcessor> handleMap = new HashMap<>();

    // MessageProcessor消息处理接口的实现类放进Map集合
    // key:tag,value:MessageProcessor实体类
    public void registerHandler(String tags, MessageProcessor messageProcessor) {
        handleMap.put(tags, messageProcessor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        MessageExt ext = list.get(0);
        String message = new String(ext.getBody());
        // 获取到tag
        String tags = ext.getTags();
        // 根据tag获取到消息处理处理类
        MessageProcessor messageProcessor = handleMap.get(tags);

        Object obj = null;
        try {
            // 将String类型的message反序列化成对应的对象
            obj = messageProcessor.transferMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("反序列化失败");
        }
        // 消息处理
        boolean result = messageProcessor.handle(obj);
        if (!result) {
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
