package com.jrymos.study.base;


import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class ClhLockStudy {

    @Test
    public void test() throws InterruptedException {
        ClhLock clhLock = new ClhLock();
        Runnable runnable = () -> {
            ClhNode node = clhLock.lock();
            try {
                Thread.sleep(1000);
                System.out.printf("%s\t%d%n", Thread.currentThread().getName(), System.currentTimeMillis() % 1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                clhLock.unlock(node);
            }
        };

        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            Thread.sleep(10);
        }
        Thread.sleep(30000);
    }


    public static class ClhLock {
        private final AtomicReference<ClhNode> tailNode = new AtomicReference<>(new ClhNode());

        public ClhNode lock() {
            ClhNode clhNode = new ClhNode();
            clhNode.locked  = true;
            ClhNode preNode;
            do {
                preNode = tailNode.get();
            } while (!tailNode.compareAndSet(preNode, clhNode));
            //自旋
            while (preNode.locked) {

            }
            return clhNode;
        }

        public void unlock(ClhNode node) {
            node.locked = false;
        }
    }


    public static class ClhNode {
        private volatile boolean locked;
    }
}
