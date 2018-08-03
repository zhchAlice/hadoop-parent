package com.wr.jvm.code.design.prodcons.lockcondition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Alice
 * @date: 2018/7/30.
 * @since: 1.0.0
 */
public class Main {
    private static final Queue<String> queue = new LinkedList<>();
    private static final Lock LOCK = new ReentrantLock();
    private static final Condition NOTFULL = LOCK.newCondition();
    private static final Condition NOTEMPTY = LOCK.newCondition();
    private static final int MAX_SIZE = 5;

    static class P0 implements Runnable {
        private AtomicInteger taskNo = new AtomicInteger(0);

        @Override
        public void run() {
            try {
                LOCK.lockInterruptibly();
                while (queue.size() == MAX_SIZE) {
                    NOTFULL.await();
                }
                String task = Thread.currentThread().getName() + ":task" + taskNo.getAndIncrement();
                queue.add(task);
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("Produce:" + task + "仓库数：" + queue.size());
                NOTEMPTY.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        }
    }

    static class C0 implements Runnable {
        @Override
        public void run() {
            try {
                LOCK.lockInterruptibly();
                while (queue.isEmpty()) {
                    NOTEMPTY.await();
                }
                String result = queue.remove();
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("Consumer:" + result + "仓库数：" + queue.size());
                NOTFULL.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new P0()).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(new C0()).start();
        }
    }
}
