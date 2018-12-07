package com.wr.jvm.code.inter.designpattern.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Alice
 * @date: 2018/12/7.
 * @since: 1.0.0
 */
public class CountDownLatchTest {

    static class Employee implements Runnable{
        private CountDownLatch countDownLatch;

        public Employee(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"到达");
            countDownLatch.countDown();
        }
    }

    static class Boss implements Runnable{
        private CountDownLatch countDownLatch;

        public Boss(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println("Boss等待");
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Boss执行");
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        executorService.execute(new Boss(countDownLatch));
        //new Thread(new Boss(countDownLatch)).start();
        for (int i=0;i<5;i++){
            executorService.execute(new Employee(countDownLatch));
        }
    }
}
