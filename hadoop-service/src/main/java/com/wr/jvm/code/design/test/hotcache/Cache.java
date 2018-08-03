package com.wr.jvm.code.design.test.hotcache;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public interface Cache {
    Object get(String key);
    boolean put(String key,Object value);
    boolean delete(String key);
    boolean clear();
}
