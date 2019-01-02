package com.yejunjie.jdk8.flatMapDemo;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author: junjieye
 * @since: 2019/1/2
 * @des
 */
public class Main {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("hello","world");

        /**
         * 把word 转为 split 的数组类型
         */
        List<String[]> result = words.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(toList());
        System.out.println(JSON.toJSONString(result));

        //




    }
}
