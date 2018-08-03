package com.wr.jvm.code.design.singleton;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class Singleton2 {
    private static Singleton2 singleton2;

    private Singleton2() {
    }

    public Singleton2 getInstance(){  //线程不安全
        if (null == singleton2) {
            return new Singleton2();
        }
        return singleton2;
    }
}