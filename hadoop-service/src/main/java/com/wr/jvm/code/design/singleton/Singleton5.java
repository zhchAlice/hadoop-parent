package com.wr.jvm.code.design.singleton;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public enum Singleton5 {
    INSTANCE;
    public Singleton5 getInstance(){
        return INSTANCE;
    }
}
