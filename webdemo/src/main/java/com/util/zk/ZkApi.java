package com.util.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * AlbertXe
 * 2019/12/4 23:33
 */
public class ZkApi implements Watcher {
    private static final String server = "192.168.199.100:2181";
    private static int timeOut = 30000;
    private static final String zkPath = "/leader";
    private ZooKeeper zk;
    private CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        ZkApi zkApi = new ZkApi();
        zkApi.createConnection(server,timeOut);
        if (zkApi.createNote(zkPath,"init")) {
            zkApi.readNote(zkPath);
            zkApi.write(zkPath,"second");
            zkApi.readNote(zkPath);
            zkApi.deleteNode(zkPath);
        }
        zkApi.releaseConnection();
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到时间通知："+event.getState());
        if (event.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }

    }

    public boolean createNote(String path, String data) {
        try {
            //节点权限 节点类型
            zk.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
            System.out.println("创建节点成功");
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * read
     * @param path
     * @return
     */
    public String readNote(String path) {
        try {
            byte[] data = zk.getData(path, false, null);
            String result = new String(data);
            System.out.println("节点内容：" + result);
            return result;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void write(String path,String data) {
        try {
            //-1 不做任何版本控制
            zk.setData(path, data.getBytes(), -1);
            System.out.println("更新数据成功");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void createConnection(String server, int timeOut) {
        this.releaseConnection();
        try {
            zk = new ZooKeeper(server, timeOut, this);
            latch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("发生 InterruptedException");
            e.printStackTrace();
        }
    }

    public void deleteNode(String path) {
        try {
            zk.delete(path,-1);
            System.out.println("删除成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    private void releaseConnection() {
        try {
            if (zk != null) {
                zk.close();
                System.out.println("关闭成功");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
