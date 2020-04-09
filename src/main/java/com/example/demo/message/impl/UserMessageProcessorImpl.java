package com.example.demo.message.impl;

import com.example.demo.entity.User;
import com.example.demo.message.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/18 11:03
 * Description：
 */
@Service
@Slf4j
public class UserMessageProcessorImpl implements MessageProcessor<User> {

    @Override
    public boolean handle(User user) {
        log.info("接收User类消息：" + user.toString());
        return true;
    }

    @Override
    public Class<User> getPresentClass() {
        return User.class;
    }
}
