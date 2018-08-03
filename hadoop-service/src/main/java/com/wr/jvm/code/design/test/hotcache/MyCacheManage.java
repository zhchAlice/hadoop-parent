package com.wr.jvm.code.design.test.hotcache;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class MyCacheManage implements CacheManage {
    private static  volatile MyCacheManage instance;

    private MyCacheManage(){}

    private Cache cache;

    public static MyCacheManage getInstance(){
        if (null == instance) {
            synchronized (MyCacheManage.class) {
                if (null == instance) {
                    instance = new MyCacheManage();
                }
            }
        }
        return instance;
    }
    @Override
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Object get(String key) {
        return this.cache.get(key);
    }

    @Override
    public boolean put(String key, Object value) {
        return this.cache.put(key,value);
    }

    @Override
    public boolean delete(String key) {
        return this.cache.delete(key);
    }

    @Override
    public boolean clear() {
        return this.cache.clear();
    }
}
