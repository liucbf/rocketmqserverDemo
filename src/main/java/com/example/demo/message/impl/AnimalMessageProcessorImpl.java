package com.example.demo.message.impl;

import com.example.demo.entity.Animal;
import com.example.demo.message.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/18 11:04
 * Description：
 */
@Service
@Slf4j
public class AnimalMessageProcessorImpl implements MessageProcessor<Animal> {

    @Override
    public boolean handle(Animal animal) {
        log.info("接收Animal类消息：" + animal.toString());
        return true;
    }

    @Override
    public Class<Animal> getPresentClass() {
        return Animal.class;
    }
}
