package com.wr.jvm.code.design.singleton;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class Singleton4 {

    private volatile static Singleton4 singleton4;

    private Singleton4(){}

    public Singleton4 getInstance(){
        if (null == singleton4) {
            synchronized (Singleton4.class){
                if (null == singleton4) {
                    return new Singleton4();
                }
                return singleton4;
            }
        }
        return singleton4;
    }

    private Object readResolve(){
        return singleton4;
    }
}
