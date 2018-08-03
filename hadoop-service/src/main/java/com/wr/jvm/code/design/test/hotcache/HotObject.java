package com.wr.jvm.code.design.test.hotcache;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class HotObject {
    private AtomicLong hot = new AtomicLong(0);
    private Object object;

    public AtomicLong getHot() {
        return hot;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void increment(){
        hot.incrementAndGet();
    }
}
