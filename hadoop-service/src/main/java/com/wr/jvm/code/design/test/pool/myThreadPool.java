package com.wr.jvm.code.design.test.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author: Alice
 * @date: 2018/7/30.
 * @since: 1.0.0
 */
public class myThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("current thread:" + Thread.currentThread().getName());
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i=0; i<10; i++) {
            executorService.execute(thread);
            executorService.submit(thread);
        }
    }
}
