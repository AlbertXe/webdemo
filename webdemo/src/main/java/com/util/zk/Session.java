package com.util.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * AlbertXe
 * 2019/12/4 23:18
 */
public class Session {
    private static final String server = "192.168.199.100:2181";
    private static int timeOut = 30000;
    private CountDownLatch latch = new CountDownLatch(1);
    @Test
    public void test() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(server, timeOut, null);
        System.out.println(zooKeeper);
        System.out.println(zooKeeper.getState());
    }

    @Test
    public void test2() throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(server, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState().equals(Event.KeeperState.SyncConnected)) {
                    latch.countDown();
                    System.out.println("已经获得了链接");
                }
            }
        });

        latch.await(); // 一直等待真正获得了链接
        System.out.println(zooKeeper.getState());

    }



}
