package com.wr.jvm;


import scala.util.parsing.combinator.testing.Str;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: Alice
 * @date: 2018/7/5.
 * @since: 1.0.0
 */
public class Singleton {
    private Singleton() {
        this(false);
    }

    private Singleton(boolean fair) {
        finalString = "123";
    }

    final String finalString;

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static class innerClass {
        private final String finalString;

        public innerClass(Singleton s) {
            this.finalString = s.finalString;
        }
    }

    private final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock readLock = rwlock.readLock();
    private final Lock writeLock = rwlock.writeLock();

    public String get(String key) {
        readLock.lock();
        writeLock.lock();
        try {
            return "111";
        } finally {
            writeLock.unlock();
            readLock.unlock();
        }

    }


    public static void main(String[] args) {
        String s1 = "H";
        String s2="llo";
        String s3=s1+s2;
        String s4="Hllo";
        String s5="H"+"llo";
        System.out.println(s3==s4);
        System.out.println(s4==s5);
    }
}
