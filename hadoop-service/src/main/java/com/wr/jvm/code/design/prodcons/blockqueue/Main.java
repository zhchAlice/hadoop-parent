package com.wr.jvm.code.design.prodcons.blockqueue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author: Alice
 * @date: 2018/7/30.
 * @since: 1.0.0
 */
public class Main {
    private static BlockingDeque<String> queue = new LinkedBlockingDeque<>(10);

    public static void main(String[] args) {
        for (int i=0; i<8; i++) {
            new Thread(new Producer(queue)).start();
        }
        for (int i=0; i<2;i++) {
            new Thread(new Consumer(queue)).start();
        }

    }
}
