package com.wr.jvm.syn.chapter4;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: Alice
 * @date: 2018/8/6.
 * @since: 1.0.0
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    private static final int MAX_WORKER_NUM = 10;
    private static final int DEFAULT_WORK_NUM = 5;
    private static final int MIN_WORKER_NUM = 1;
    private final LinkedList<Job> jobs = new LinkedList<>();
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    private int workerNum = DEFAULT_WORK_NUM;
    private AtomicLong threadNum = new AtomicLong(0);

    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORK_NUM);
    }

    private void initializeWorkers(int workerNum) {
        for (int i=0; i<workerNum; i++){
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (null != jobs){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker:workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            if (num + workerNum > MAX_WORKER_NUM){
                num = MAX_WORKER_NUM - workerNum;
            }
            initializeWorkers(num);
            workerNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs){
            if (num > workerNum) {
                num = workerNum;
            }
            int count = 0;
            int index = 0;
            while (count<num && index<workerNum){
                Worker worker = workers.get(index);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
                index++;
            }
            workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    class Worker implements Runnable{
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running){
                Job job = null;
                synchronized (jobs){
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                    if (null != job){
                        job.run();
                    }
                }
            }

        }

        public void shutdown(){
            running = false;
        }
    }
}
