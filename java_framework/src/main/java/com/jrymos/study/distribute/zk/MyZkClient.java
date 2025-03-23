package com.jrymos.study.distribute.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.function.BiConsumer;

public class MyZkClient {
    private static final String ZK_ADDRESS = "localhost:2181";
    private static final String CONFIG_PATH = "/app_config";
    private static final int SESSION_TIMEOUT = 5000;

    // 添加等待连接建立的逻辑
    private void waitForConnected(ZooKeeper zk) throws InterruptedException {
        while (zk.getState() != ZooKeeper.States.CONNECTED) {
            Thread.sleep(100);
        }
    }

    @Test
    public void test() throws Exception {
        ZooKeeper zk = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("连接状态变化：" + event.getType());
            }
        });

        // 等待连接完全建立
        waitForConnected(zk);

        // 创建节点时获取实际路径
        String path = zk.create(CONFIG_PATH, "hello world!".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("实际创建路径：" + path);

        // 使用 exists() 检查节点是否存在
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            throw new RuntimeException("节点不存在: " + path);
        }

        // 通过 Stat 对象获取数据（避免版本冲突）
        byte[] data = zk.getData(path, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("stat数据发生变化：" + event.getPath() + "\t" + event.getType() + "\t" + event.getState());
            }
        }, stat);


        System.out.println("节点数据：" + new String(data, StandardCharsets.UTF_8));

        // 更新数据时传递正确的版本号
        Stat newStat = zk.setData(path, "well".getBytes(StandardCharsets.UTF_8), stat.getVersion());
        System.out.println("版本号更新：" + newStat.getVersion());

        Thread.sleep(10000);
        zk.close();
    }


    @Test
    public void test2() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            System.out.println("连接状态变化：" + event.getPath() + "\t" + event.getType() + "\t" + event.getState());
            // **此处无需重新注册**，会话级 Watcher 长期有效
        });
        // 等待连接完全建立
        waitForConnected(zk);
        MyWatchZk myWatchZk = new MyWatchZk();
        myWatchZk.zk = zk;
        myWatchZk.consumer = (s, s2, stat) -> {
            try {
                Thread.sleep(100);
                System.out.printf("version:%d\t%s\t%s\t%s\t%n", stat.getVersion(), Thread.currentThread(), s, s2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        String path = CONFIG_PATH + "/test2";
        // 使用 exists() 检查节点是否存在
        Stat stat = zk.exists(path, myWatchZk);
        if (stat == null) {
            if (zk.exists(CONFIG_PATH, false) == null) {
                zk.create(CONFIG_PATH, "hello world!".getBytes(StandardCharsets.UTF_8),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            // 创建节点时获取实际路径,需要先建父节点，否则：KeeperErrorCode = NoNode for /app_config/test2
            path = zk.create(path, "hello world!".getBytes(StandardCharsets.UTF_8),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("实际创建路径：" + path);
        }
        stat = zk.exists(path, false);


        for (int i = 0; i < 100; i++) {
            //version是递增的
            stat = zk.setData(path, ("index:" + i).getBytes(StandardCharsets.UTF_8), stat.getVersion());
            System.out.println("setData:" + i + "\t" + stat.getVersion() + "\t");
            Thread.sleep(10);
        }
        Thread.sleep(30000);
    }


    public static class MyWatchZk implements Watcher {
        private ZooKeeper zk;
        private Consumer consumer;

        @Override
        public void process(WatchedEvent event) {
            try {
                Stat stat = new Stat(); //zk取到data信息回拷贝到stat中
                byte[] data = zk.getData(event.getPath(), this, stat);
                consumer.consume(event.getPath(), new String(data, StandardCharsets.UTF_8), stat);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    interface Consumer {
        void consume(String path, String value, Stat stat);
    }
}
