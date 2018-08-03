package com.wr.jvm.code.design.prodcons.blockqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Alice
 * @date: 2018/7/30.
 * @since: 1.0.0
 */
public class Producer implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private AtomicInteger taskNo = new AtomicInteger(0);

    public Producer(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true){
            try {
                String task = Thread.currentThread().getName()+":task"+taskNo.getAndIncrement();
                blockingQueue.put(task);
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("Produce:" + task +"仓库数："+blockingQueue.size() );
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
