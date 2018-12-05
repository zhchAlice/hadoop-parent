package com.wr.jvm.code.inter.designpattern.singleton;

/**
 * @author: Alice
 * @date: 2018/12/5.
 * @since: 1.0.0
 */
public class StaticInnerClassSingleton {
    /*懒汉式，利用静态内部类实现延迟加载*/
    private StaticInnerClassSingleton(){}
    private static class Singleton2Holder{
        private static StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }
    public static StaticInnerClassSingleton getInstance(){
        return Singleton2Holder.instance;
    }
}
