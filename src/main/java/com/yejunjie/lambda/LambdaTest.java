package com.yejunjie.lambda;

import java.util.ArrayList;
import java.util.List;

public class LambdaTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        list.forEach(x-> System.out.println(x));


    }
}
