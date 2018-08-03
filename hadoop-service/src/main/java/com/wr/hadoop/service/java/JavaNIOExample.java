package com.wr.hadoop.service.java;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author: Alice
 * @date: 2018/6/14.
 * @since: 1.0.0
 */
public class JavaNIOExample {

    public void method1() throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream
                    (this.getClass().getClassLoader().getResource("javaNioTest.txt").getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Selector selector = Selector.open();
        ServerSocketChannel  ssc= ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8070));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }
}
