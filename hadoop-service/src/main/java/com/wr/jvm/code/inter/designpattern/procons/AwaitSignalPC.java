package com.wr.jvm.code.inter.designpattern.procons;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Alice
 * @date: 2018/12/5.
 * @since: 1.0.0
 */
public class AwaitSignalPC {
    private final LinkedList<Object> resources = new LinkedList<>();//缓冲区资源
    private final int MAX_VALUE = 10;
    private final Lock lock = new ReentrantLock();
    private final Condition NOT_FULL = lock.newCondition();
    private final Condition NOT_EMPTY = lock.newCondition();

    public void produce(int num){
        try {
            lock.lockInterruptibly();
            while (resources.size()+num>MAX_VALUE){
                NOT_FULL.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
