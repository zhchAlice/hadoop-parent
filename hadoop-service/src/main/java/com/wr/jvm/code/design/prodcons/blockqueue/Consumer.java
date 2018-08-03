package com.wr.jvm.code.design.prodcons.blockqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author: Alice
 * @date: 2018/7/30.
 * @since: 1.0.0
 */
public class Consumer implements Runnable {
    private BlockingQueue<String> blockingQueue;

    public Consumer(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String result = blockingQueue.take();
                Thread.sleep(500 + (long) (Math.random() * 500));
                System.out.println("Consumer:" + result +"仓库数："+blockingQueue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
