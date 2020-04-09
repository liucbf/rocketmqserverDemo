package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Author： NiuBen
 * Date： Created in 2020/3/18 11:07
 * Description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Animal implements Serializable {

    private String name;

    private Integer age;

    private String time;

}
