package com.wr.jvm.code.inter.designpattern.procons;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: Alice
 * @date: 2018/12/6.
 * @since: 1.0.0
 */
public class BlockQueuePC {
    private BlockingQueue<Object> resources;

    private BlockQueuePC(int max_size) {
        resources = new LinkedBlockingQueue<>(max_size);
    }

    public void produce(){
        try {
            resources.put(new Object());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void consume(){
        try {
            resources.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Producer implements Runnable{
        private BlockQueuePC blockQueuePC;
        Producer(BlockQueuePC pc){
            blockQueuePC = pc;
        }

        @Override
        public void run() {
            while (true) {
                blockQueuePC.produce();
            }
        }
    }

    static class Consumer implements Runnable{
        private BlockQueuePC blockQueuePC;

        Consumer(BlockQueuePC blockQueuePC) {
            this.blockQueuePC = blockQueuePC;
        }

        @Override
        public void run() {
            while (true) {
                blockQueuePC.consume();
            }
        }
    }

    public static void main(String[] args) {
        BlockQueuePC blockQueuePC = new BlockQueuePC(10);
        for (int i=0;i<5;i++){
            new Thread(new Producer(blockQueuePC)).start();
        }
        for (int i=0;i<5;i++){
            new Thread(new Consumer(blockQueuePC)).start();
        }
    }
}
