package com.wr.jvm.code.inter.designpattern.singleton;

/**
 * @author: Alice
 * @date: 2018/12/5.
 * @since: 1.0.0
 */
public class Singleton1 {
    /*饿汉式单例，类加载时已经完成实例初始化*/
    private Singleton1() {
    }

    private static Singleton1 instance = new Singleton1();

    public static Singleton1 getInstance(){
        return instance;
    }
}
