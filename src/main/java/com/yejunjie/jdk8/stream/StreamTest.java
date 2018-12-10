package com.yejunjie.jdk8.stream;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * http://www.importnew.com/17313.html
 *
 * @author: junjieye
 * @since: 2018/11/23
 * @des
 */
public class StreamTest {


    public static void main(String[] args) {

        StudentDTO studentDTO = StudentDTO.builder()
                .chineseScore(100)
                .mathScore(80)
                .id("s1")
                .name("tom")
                .area("zhejiang")
                .build();


        StudentDTO studentDTO1 = StudentDTO.builder().
                chineseScore(90)
                .mathScore(70)
                .id("s2")
                .name("smith")
                .area("anhui")
                .build();

        StudentDTO studentDTO2 = StudentDTO.builder().
                chineseScore(80)
                .mathScore(60)
                .id("s2")
                .name("john")
                .area("anhui")
                .build();

        StudentDTO studentDTO3 = StudentDTO.builder().
                chineseScore(70)
                .mathScore(50)
                .id("s3")
                .name("son")
                .area("sichuan")
                .build();

        List<StudentDTO> studentDTOS = Arrays.asList(studentDTO,studentDTO1,studentDTO2,studentDTO3);

        listToMap(studentDTOS);
        partitionTest(studentDTOS);
    }


    /**
     * 基于list的stream的操作
     * @param students
     */
    public static void listToMap(List<StudentDTO> students){
        //遍历
        students.forEach(x-> System.out.println(x.getChineseScore()));

        //list 中 对某一个字段分组
        Map<String,List<StudentDTO>> groupMap = students.stream().collect(groupingBy(StudentDTO::getId));
        System.out.println(JSON.toJSONString(groupMap));

        //list中 对一个字段分组  并计算每一个组的成员个数
        Map<String,Long> countMap = students.stream().collect(groupingBy(StudentDTO::getId,counting()));
        System.out.println(countMap);

        //list中 对一个字段分组  计算某一个字段的平均值
        Map<String,Double> averMap = students.stream().collect(groupingBy(StudentDTO::getId,averagingInt(StudentDTO::getMathScore)));
        System.out.println(averMap);

        //list 先根据某一个字段去重 再对某一个字段进行排序 最后输出


    }

    public static void mapToList(Map<String,StudentDTO> studentMap){
        studentMap.forEach((k,v)-> {
            System.out.println(k);
            System.out.println(v);
        });

        studentMap.forEach((k,v)-> System.out.println(k+"->"+v));

    }


    /**
     * partition 分区功能
     * 分区是一种特殊的分组，结果 map 至少包含两个不同的分组——一个true，一个false
     * 用一个条件比如分数大于60分，把学生分为及格和不及格两个部分
     * @param students
     */
    public static void partitionTest(List<StudentDTO> students){
        //按照语文成绩是否大于90 分为两个分区
        Map<Boolean, List<StudentDTO>> partitioned =
                students.stream().collect(partitioningBy(e -> e.getChineseScore() > 90));
        System.out.println(JSON.toJSONString(partitioned));

        //按照语文成绩是否大于90 分为两个分区，再统计每一个分区中每个地区的人数
        Map<Boolean, Map<String,Long>> partitionCount =
                students.stream().collect(partitioningBy(e -> e.getChineseScore() > 90,
                        groupingBy(StudentDTO::getArea,counting())));
        System.out.println(JSON.toJSONString(partitionCount));
    }


}
