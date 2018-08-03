package com.wr.jvm.code.design.test.hotcache;

/**
 * @author: Alice
 * @date: 2018/7/28.
 * @since: 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        MyCacheManage myCacheManage = MyCacheManage.getInstance();
        myCacheManage.setCache(new MyCache(10L));
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myCacheManage.put("java","java编程思想");
                myCacheManage.put("java1","java编程思想");
                myCacheManage.put("java2","java编程思想");
                myCacheManage.put("java3","java编程思想");
                myCacheManage.put("java4","java编程思想");
                myCacheManage.put("scala","scala编程思想");
                myCacheManage.put("scala1","scala编程思想");
                Object value=myCacheManage.get("java");
                //myCacheManage.delete("java");
                myCacheManage.put("java","java编程思想");
                //myCacheManage.clear();

            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myCacheManage.put("java","java编程思想");
                myCacheManage.put("java5","java编程思想");
                myCacheManage.put("java6","java编程思想");
                myCacheManage.put("java7","java编程思想");
               // myCacheManage.delete("java");
                Object value=myCacheManage.get("java");
                myCacheManage.put("scala2","scala编程思想");
                myCacheManage.put("scala3","scala编程思想");
                myCacheManage.put("scala4","scala编程思想");
                myCacheManage.put("scala5","scala编程思想");
                value=myCacheManage.get("scala");
                myCacheManage.put("java8","java编程思想");
                myCacheManage.clear();

            }
        });
        thread1.start();
        thread2.start();

    }
}
