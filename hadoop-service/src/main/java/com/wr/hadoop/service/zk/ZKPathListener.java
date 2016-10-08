package com.wr.hadoop.service.zk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * ZK监听器
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/21.
 */
public class ZKPathListener implements PathChildrenCacheListener {
    private static final Log LOG = LogFactory.getLog(ZKPathListener.class);
    private PathChildrenCache pathChildrenCache;
    private int curIndex = 0;

    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        switch (event.getType()) {
            case CHILD_ADDED:
                //监听到新增节点时，为该节点写入编号值
                String curPath = event.getData().getPath();
                client.setData().forPath(curPath, String.valueOf(curIndex).getBytes());
                LOG.info(String.format("Add path{%s} with index:", curPath, curIndex));
                curIndex++;
                break;
            default:
                break;
        }
    }

    public void setPathChildrenCache(PathChildrenCache pathChildrenCache) {
        this.pathChildrenCache = pathChildrenCache;
    }
}
