package com.wr.jvm.code.design.singleton;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class Singleton {

    private static final Singleton singleton = new Singleton();

    public Singleton getInstance(){
        return singleton;
    }
}

