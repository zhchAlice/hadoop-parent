package com.wr.jvm.code.design.singleton;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class Singleton3 {
    private static Singleton3 singleton3;

    private Singleton3(){}

    public static synchronized Singleton3 getInstance(){   //同步粒度太大，没有进入if的代码块不需要
        if (null == singleton3) {
            return new Singleton3();
        }
        return singleton3;
    }
}
