package com.wr.jvm.syn.chapter4;

/**
 * @author: Alice
 * @date: 2018/8/6.
 * @since: 1.0.0
 */
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);
    void shutdown();
    void addWorkers(int num);
    void removeWorkers(int num);
    int getJobSize();
}
