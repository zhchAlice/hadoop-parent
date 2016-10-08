package com.wr.hadoop.service.zk;

import com.wr.hadoop.common.CommonParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ZK监听器
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/21.
 */
public class ZKUtil {
    private static final Log LOG = LogFactory.getLog(ZKUtil.class);

    private static final String zks = "zk01:2181,zk02:2181,zk03:2181";
    private static CuratorFramework zkclient = null;
    private static final String ZK_WORDS_ROOT_PATH = "/words/";

    private static ZKUtil zkUtil;
    private final static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 100, 5l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());

    public ZKUtil() {
    }

    public static ZKUtil getInstance(){
        if (zkUtil == null) {
            zkUtil = new ZKUtil();
            initZKClient();
            try {
                watchChild(ZK_WORDS_ROOT_PATH);
            } catch (Exception e) {
                LOG.error(String.format("watchChild of path {%s} fail:", ZK_WORDS_ROOT_PATH, e.getMessage()));
                return null;
            }
        }
        return zkUtil;
    }

    private static void initZKClient() {
        if (zkclient == null) {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            zkclient = CuratorFrameworkFactory.builder().connectString(zks).sessionTimeoutMs(5000)
                    .retryPolicy(retryPolicy).namespace("myapp").build();
            zkclient.start();
        }
    }

    /**
     * 启动线程池监听节点变化  //TODO待优化--改成单线程监听节点变化
     * @param path
     * @throws Exception
     */
    public static void watchChild(String path) throws Exception {
        //若监听节点不存在，则先创建待监听节点
        if (zkclient.checkExists().forPath(path) == null) {
            zkclient.create().forPath(path);
        }
        PathChildrenCache childrenCache = new PathChildrenCache(zkclient, path, true, false, executor);
        ZKPathListener listener = new ZKPathListener();
        listener.setPathChildrenCache(childrenCache);
        childrenCache.getListenable().addListener(listener, executor);
        childrenCache.start();
    }

    public static CuratorFramework getZkclient() {
        return zkclient;
    }
}
