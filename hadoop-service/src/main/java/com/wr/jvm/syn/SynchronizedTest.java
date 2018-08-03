package com.wr.jvm.syn;

/**
 * @author: Alice
 * @date: 2018/7/20.
 * @since: 1.0.0
 */
public class SynchronizedTest {
    public synchronized void doSth(){
        System.out.println("hello");
    }
    public void doSth1(){
        synchronized (SynchronizedTest.class) {
            System.out.println("world");
        }
    }

    public static void main(String[] args) {
        Initilization sca = new Initilization();
    }


}
 class Initilization{
    static {
        System.out.println("init");
    }
}



