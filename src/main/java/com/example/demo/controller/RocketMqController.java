package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Animal;
import com.example.demo.entity.User;
import com.example.demo.mqconfigdemo.UserProducerConfig;
import com.example.demo.producer.RocketMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/17 16:30
 * Description：
 */
@RestController
@Slf4j
public class RocketMqController {

    @Autowired
    private UserProducerConfig userProducerConfig;

    @GetMapping("/test")
    public void TestSend() {
        DefaultMQProducer producer = userProducerConfig.getDefaultProducer();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = new User("小明22", 20, "男", sdf.format(new Date()));
        Message messageToUser = new Message("topic2020", "test2020", JSON.toJSONString(user).getBytes());
        try {
            producer.send(messageToUser);
            System.out.println("消息1发送成功："+user.toString());
        } catch (Exception e) {
            log.error("消息发送异常");
            e.printStackTrace();
        }
/*
        Animal animal = new Animal("金毛11", 3, sdf.format(new Date()));
        Message messageToAnimal = new Message("2020test", "animal", JSON.toJSONString(animal).getBytes());
        try {
            producer.send(messageToAnimal);
            System.out.println("消息2发送成功："+animal.toString());

        } catch (Exception e) {
            log.error("消息发送异常");
            e.printStackTrace();
        }*/

    }

}
