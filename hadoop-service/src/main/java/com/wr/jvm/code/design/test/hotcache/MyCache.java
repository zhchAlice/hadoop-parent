package com.wr.jvm.code.design.test.hotcache;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class MyCache implements Cache {

    private Long maxSize;

    private volatile AtomicLong currentSize=new AtomicLong(0);

    private Map<String, HotObject> data = new ConcurrentHashMap<>();

    MyCache(Long maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public Object get(String key) {
        HotObject value = data.get(key);
        if (null == value) {
            return null;
        }
        value.increment();
        return value.getObject();
    }

    @Override
    public synchronized boolean put(String key, Object value) {
        if (data.containsKey(key)) {
            HotObject existValue = data.get(key);
            existValue.increment();
            data.put(key,existValue);
            System.out.println("put<"+key+","+value+">");
            return true;
        }
        if (currentSize.get() >= maxSize) {
            deleteLeastUse();
        }
        HotObject hotObject = new HotObject();
        hotObject.setObject(value);
        hotObject.increment();
        data.put(key,hotObject);
        System.out.println("put<"+key+","+value+">,current cache size:"+currentSize);
        currentSize.incrementAndGet();
        return true;
    }

    private synchronized void deleteLeastUse() {
        List<Map.Entry<String,HotObject>> mapAsList = new ArrayList<>(data.entrySet());
        mapAsList.sort(new Comparator<Map.Entry<String, HotObject>>() {
            @Override
            public int compare(Map.Entry<String, HotObject> o1, Map.Entry<String, HotObject> o2) {
                return (int) (o1.getValue().getHot().get() - o2.getValue().getHot().get());
            }
        });
        String key = mapAsList.get(0).getKey();
        System.out.println("least user delete "+key);
        data.remove(key);
        currentSize.decrementAndGet();
    }

    @Override
    public synchronized boolean delete(String key) {
        data.remove(key);
        currentSize.decrementAndGet();
        return true;
    }

    @Override
    public synchronized boolean clear() {
        data.clear();
        currentSize.set(0);
        return true;
    }
}
