package com.yangxinyu.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @BelongsProject : lambda
 * @BelongsPackage : com.yangxinyu.test
 * @Date : 2023/3/6 13:43
 * @Author : 星宇
 * @Description :
 */
public class TestLambda {
    @Test
    public void test01(){
        String[] arr = {"hello","Java","yang","lambda","java"};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void test02(){
        String[] arr = {"hello","Java","yang","lambda","java"};
        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test03(){
        String[] arr = {"hello","Java","yang","lambda","java"};
        Arrays.sort(arr, (String o1, String o2)->o1.compareToIgnoreCase(o2));
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void test04(){
        String[] arr = {"hello","Java","yang","lambda","java"};
        Arrays.sort(arr, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test05(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("多线程！");
            }
        });
        thread.start();
    }

    @Test
    public void test06(){
        /*
        1、明确函数式接口：Runnable接口
        2、明确函数式接口类型：普通接口（抽象没有参数没有返回值）
        3、明确接口，省略接口名
         */
        Thread thread = new Thread(()->{
            System.out.println("多线程！");
        });

        thread.start();
    }

    @Test
    public void test07(){
        Thread thread = new Thread(()->System.out.println("多线程！"));
        thread.start();
    }

    @Test
    public void test08(){
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("Java");
        stringList.add("lambda");
        stringList.add("java");
        stringList.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("a");
            }
        });

        System.out.println(stringList);
    }
    @Test
    public void test09(){
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("Java");
        stringList.add("lambda");
        stringList.add("java");
       /* stringList.removeIf((String s)->{
            return s.contains("a");
        });*/
        //return语句省略
        stringList.removeIf((String s)->s.contains("a"));
        stringList.removeIf((s)->s.contains("a"));
        stringList.removeIf(s->s.contains("a"));
        System.out.println(stringList);
    }
}
