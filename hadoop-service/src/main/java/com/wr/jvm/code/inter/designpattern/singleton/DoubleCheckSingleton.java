package com.wr.jvm.code.inter.designpattern.singleton;

/**
 * @author: Alice
 * @date: 2018/12/5.
 * @since: 1.0.0
 */
public class DoubleCheckSingleton {
    /*双重锁检查单例*/
    private DoubleCheckSingleton(){}
    private static volatile DoubleCheckSingleton instance;
    public static DoubleCheckSingleton getInstance(){
        if (null == instance){
            synchronized(DoubleCheckSingleton.class){
                if (null == instance){
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
