package com.yejunjie.lambda;

import com.yejunjie.lambda.intefaces.AppleFilter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class TestMain {

    public static void main(String[] args) {

        List<Apple> apples = new ArrayList<>();

        // 筛选苹果
        List<Apple> filterApples = filterApplesByAppleFilter(apples, new AppleFilter() {
            @Override
            public boolean accept(Apple apple) {
                // 筛选重量大于100g的红苹果
                return Color.RED.equals(apple.getColor()) && apple.getWeight() > 100;
            }
        });

        // 筛选苹果
        // 上面的匿名函数 可以使用lambda表达式来代替实现
        // (Object object)-> 来代替上面的匿名函数
        List<Apple> filterApple = filterApplesByAppleFilter(apples,
                (Apple apple) -> Color.RED.equals(apple.getColor()) && apple.getWeight() >= 100);
                //可以省略参数类型 编译器会匹配
//                apple -> Color.RED.equals(apple.getColor()) && apple.getWeight() >= 100);

    }

    //筛选绿色
    public static List<Apple> filterGreenApples(List<Apple> apples) {
        List<Apple> filterApples = new ArrayList<>();
        for (final Apple apple : apples) {
            // 筛选出绿色的苹果
            if (Color.GREEN.equals(apple.getColor())) {
                filterApples.add(apple);
            }
        }
        return filterApples;
    }

    //筛选红色
    public static List<Apple> filterRedApples(List<Apple> apples) {
        List<Apple> filterApples = new ArrayList<>();
        for (final Apple apple : apples) {
            // 筛选出红色的苹果
            if (Color.RED.equals(apple.getColor())) {
                filterApples.add(apple);
            }
        }
        return filterApples;
    }

    //把颜色当作参数传入
    public static List<Apple> filterApplesByColor(List<Apple> apples, Color color) {
        List<Apple> filterApples = new ArrayList<>();
        for (final Apple apple : apples) {
            // 依据传入的颜色参数进行筛选
            if (color.equals(apple.getColor())) {
                filterApples.add(apple);
            }
        }
        return filterApples;
    }

    //把颜色和重量当作参数传入
    public static List<Apple> filterApplesByColorAndWeight(List<Apple> apples, Color color, float weight) {
        List<Apple> filterApples = new ArrayList<>();
        for (final Apple apple : apples) {
            // 依据颜色和重量进行筛选
            if (color.equals(apple.getColor()) && apple.getWeight() >= weight) {
                filterApples.add(apple);
            }
        }
        return filterApples;
    }


    // 应用过滤器的筛选方法
    public static List<Apple> filterApplesByAppleFilter(List<Apple> apples, AppleFilter filter) {
        List<Apple> filterApples = new ArrayList<>();
        for (final Apple apple : apples) {
            if (filter.accept(apple)) {
                filterApples.add(apple);
            }
        }
        return filterApples;
    }



}
