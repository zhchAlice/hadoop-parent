package com.wr.jvm.code.design.prodcons.objectwaitnotify;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Alice
 * @date: 2018/7/30.
 * @since: 1.0.0
 */
public class Main {

    private static final int MAX_SIZE = 5;
    private static final Queue<String> queue = new LinkedList<>();


    static class Producer implements Runnable {

        private AtomicInteger taskNo = new AtomicInteger(0);

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    try {
                        while (queue.size() == MAX_SIZE) {
                            queue.wait();
                        }
                        String task = Thread.currentThread().getName() + ":task" + taskNo.getAndIncrement();
                        queue.add(task);
                        Thread.sleep(new Random().nextInt(1000));
                        System.out.println("Produce:" + task + "仓库数：" + queue.size());
                        queue.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    try {
                        while (queue.isEmpty()) {
                            queue.wait();
                        }
                        String result = queue.remove();
                        Thread.sleep(500 + (long) (Math.random() * 500));
                        System.out.println("Consumer:" + result + "仓库数：" + queue.size());
                        queue.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Producer()).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(new Consumer()).start();
        }
    }
}
