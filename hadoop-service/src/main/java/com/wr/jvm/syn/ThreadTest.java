package com.wr.jvm.syn;

import java.util.Vector;

/**
 * @author: Alice
 * @date: 2018/7/21.
 * @since: 1.0.0
 */
public class ThreadTest {
    private static Vector<Integer> vector = new Vector<>();
    public static void main(String[] args) {
        while (true){
            for (int i=0; i<10;i++) {
                vector.add(i);
            }
            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<vector.size();i++){
                        vector.remove(i);
                    }
                }
            });
            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<vector.size();i++){
                        System.out.println(vector.get(i));
                    }
                }
            });
            removeThread.start();
            printThread.start();

            while (Thread.activeCount()>20);
        }
    }
}
