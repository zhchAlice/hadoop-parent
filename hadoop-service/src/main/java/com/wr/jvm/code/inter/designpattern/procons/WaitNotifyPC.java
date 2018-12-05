package com.wr.jvm.code.inter.designpattern.procons;

import java.util.LinkedList;

/**
 * @author: Alice
 * @date: 2018/12/5.
 * @since: 1.0.0
 */
public class WaitNotifyPC {
    /*使用wait和notify实现生产者和消费者模式*/
    private final LinkedList<Object> resources = new LinkedList<>();//缓冲区资源
    private final int MAX_VALUE = 10;

    public void produce(int num){
        synchronized (resources){
            while (resources.size()+num>MAX_VALUE){
                try {
                    System.out.println(resources.size()+"+"+num+">MAX_VALUE,等待");
                    resources.wait();//阻塞等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i=0;i<num; i++){
                resources.addLast(new Object());
            }
            System.out.println(Thread.currentThread().getName()+"生产"+num);
            resources.notifyAll();
        }
    }

    public void consume(int num){
        synchronized(resources){
            while (resources.size()<num){
                try {
                    System.out.println(resources.size()+"<"+num+"等待");
                    resources.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i=0;i<num;i++){
                resources.remove();//poll和remove的区别在于在队首元素不存在的情况下，前者返回null;后者抛出异常
            }
            resources.notifyAll();
            System.out.println(Thread.currentThread().getName()+"消费"+num);
        }
    }

    static class Producer implements Runnable{
        private WaitNotifyPC waitNotifyPC;

        private int num;

        public Producer(WaitNotifyPC waitNotifyPC, int num) {
            this.waitNotifyPC = waitNotifyPC;
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                waitNotifyPC.produce(num);
            }
        }
    }

    static class Consumer implements Runnable{
        private WaitNotifyPC waitNotifyPC;

        private int num;

        public Consumer(WaitNotifyPC waitNotifyPC, int num) {
            this.waitNotifyPC = waitNotifyPC;
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                waitNotifyPC.consume(num);
            }
        }
    }

    public static void main(String[] args) {
        WaitNotifyPC waitNotifyPC = new WaitNotifyPC();
        for (int i=0;i<5;i++){
            new Thread(new Producer(waitNotifyPC,2)).start();
        }
        for (int i=0;i<5;i++){
            new Thread(new Consumer(waitNotifyPC,3)).start();
        }
    }

}
