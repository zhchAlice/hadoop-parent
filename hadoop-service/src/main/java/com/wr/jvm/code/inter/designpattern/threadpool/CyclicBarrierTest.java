package com.wr.jvm.code.inter.designpattern.threadpool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: Alice
 * @date: 2018/12/7.
 * @since: 1.0.0
 */
public class CyclicBarrierTest {
    static class CyclicBarrierThread implements Runnable{
        private CyclicBarrier cyclicBarrier;

        CyclicBarrierThread(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"到了");
            try {
                cyclicBarrier.await();
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("人都到齐了"));
        for (int i=0;i<5;i++){
            new Thread(new CyclicBarrierThread(cyclicBarrier)).start();
        }
    }

}
