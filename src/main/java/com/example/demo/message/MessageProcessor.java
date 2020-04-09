package com.example.demo.message;

import com.example.demo.utils.JsonUtils;

public interface MessageProcessor<T> {

    boolean handle(T messageExt);

    Class<T> getPresentClass();

    // 将String类型的message反序列化成对应的对象
    default T transferMessage(String message) {
        return JsonUtils.fromJson(message, getPresentClass());
    }

}
