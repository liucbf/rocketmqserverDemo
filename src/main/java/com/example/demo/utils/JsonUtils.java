package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/18 10:59
 * Description：jackson序列化工具
 */
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转为json
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     */
    public static <T> T fromJson(String json, Class<T> presentClass) {
        try {
            return MAPPER.readValue(json, presentClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
