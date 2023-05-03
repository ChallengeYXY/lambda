package com.yangxinyu.test;


import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @BelongsProject : lambda
 * @BelongsPackage : com.yangxinyu.test
 * @Date : 2023/3/6 13:43
 * @Author : 星宇
 * @Description :
 */
public class TestLambda {
    /**
     * Arrays工具类：public static <T> void sort(T[] a, Comparator<? super T> c)
     * Comparator接口：int compare(T o1, T o2);
     * 采用匿名内部类的方法
     */
    @Test
    public void test01(){
        String[] arr = {"hello","Java","yang","lambda","java"};
        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 发现这个匿名内部类new Comparator<String>()不会在别的地方使用了
     * 而且泛型类型也会在T[] a时进行明确
     * 发现有一些多余代码
     */
    @Test
    public void test02(){
        /*
            1、明确函数函数接口：Comparator<T>接口（从public static <T> void sort(T[] a, Comparator<? super T> c)可知）
            2、明确接口方法：int compare(T o1, T o2);
            3、判断接口方法类型：有参数有返回值（功能型接口）
            4、推测接口方法的参数与返回值的类型：
                String[] arr
                ==>推测出Comparator<T>的T为String且必须为String的父类或接口
                ==>推测出int compare(T o1, T o2);的T为String
            5、函数接口方法的形参个数，函数体是无法推测出来的
            6、由于上面这些都可以推测出来，可以推测出来就可以直接在代码中省略
         */
        String[] arr = {"hello","Java","yang","lambda","java"};
        Arrays.sort(arr,(String o1, String o2)->{
            return o1.compareToIgnoreCase(o2);
        });
        System.out.println(Arrays.toString(arr));

        /*
            1、接口方法参数列表的参数类型是可以进行推断的，写上不错，不写也行
            2、接口方法的方法体只有一句代码时，可以直接将{}省略，有return的还可以将return省略
         */
        Arrays.sort(arr,((o1, o2) -> o2.compareToIgnoreCase(o1)));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * Thread的构造器：Thread(Runnable target)
     * Thread接口的接口方法：void run();
     * 采用匿名内部类的方法
     */
    @Test
    public void test03(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是一个线程！");
            }
        });
        thread.start();
    }
    @Test
    public void test04(){
        Thread thread = new Thread(()->System.out.println("我是一个线程！"));
        thread.start();
    }


    /**
     * 判断型接口
     * List<E>实例对象：boolean removeIf(Predicate<? super E> filter)
     * Predicate<T>接口的接口方法：boolean test(T t)
     * 匿名内部类的方式
     */
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
        System.out.println(stringList);
        /*
            1、List<String>
                ==》推出boolean removeIf(Predicate<? super E> filter)的E为大于等于String
                ==》推出Predicate<T>的T大于等于String
                ==》推出boolean test(T t)的T大于等于String
         */
        stringList.removeIf(s -> s.contains("o"));
        System.out.println(stringList);
    }

    /**
     * 消费型接口
     */
    @Test
    public void test10(){
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("Java");
        stringList.add("lambda");
        stringList.add("java");
        /*
        for (String s : stringList) {
            System.out.println(s);
        }
        */
        //上面的写法就是调用了Iterable<T>接口中的forEach方法
        stringList.forEach(s -> {
            if (s.contains("a")){
                System.out.println(s);
            }
        });
    }

    /**
     * 供给型接口
     */
    @Test
    public void test11(){
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "杨鑫宇";
            }
        };
        System.out.println(supplier.get());
    }
    @Test
    public void test12(){
        //循环调用get()方法
        Stream.generate(()->"杨鑫宇").forEach(s-> System.out.println(s));
    }

    /**
     * 功能型接口
     */
    @Test
    public void test13(){
        //将单词首字母转为大写
        //Function<T,R>类的抽象方法R apply(T t) 有参有返回值
        Function<String,String> function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return Character.toUpperCase(s.charAt(0))+s.substring(1);
            }
        };
        System.out.println(function.apply("hello"));
    }

    @Test
    public void test14(){
        Function<String,String> function = s -> Character.toUpperCase(s.charAt(0))+s.substring(1);
        System.out.println(function.apply("hello"));
    }

    @Test
    public void test15(){
        Function<Student, String> getName = new Function<Student, String>() {
            @Override
            public String apply(Student student) {
                return student.getName();
            }
        };
        System.out.println(getName.apply(new Student("张三",11)));

        Function<Student, String> getName1 = Student::getName;
    }

    @Test
    public void test16(){
        List<Student> students = new ArrayList<>();
        students.add(new Student("张三",11));
        students.add(new Student("李四",11));
        String s = JsonUtils.toJsonString(students);
        List list = JsonUtils.parseObject(s, List.class);
        for (Object o : list) {
            JsonUtils.parseObject(JsonUtils.toJsonString(o),Student.class);
        }
    }

    @Test
    public void test17(){
        String s = DigestUtils.md5Hex("123");
        System.out.println(s);
    }

    @Test
    public void test18(){
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("Java");
        stringList.add("lambda");
        stringList.add("java");

        //创建流
        Stream<String> stream = stringList.stream();
        /*
            操作流
            还会返回一个新流（虽然不会改变原有集合，但是新流中的数据会变）
            一个流只能被操作一次（操作完成就废弃）
         */
        stream = stream.filter(s -> s.contains("o"));

        //终结流
        stream.forEach(s -> System.out.println(s));

    }
}
