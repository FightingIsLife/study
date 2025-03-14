package com.jrymos.study.base;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class McsLockStudy {

    @Test
    public void test() throws InterruptedException {
        McsLock mcsLock = new McsLock();
        Runnable runnable = () -> {
            McsNode node = mcsLock.lock();
            try {
                Thread.sleep(1000);
                System.out.printf("%s\t%d%n", Thread.currentThread().getName(), System.currentTimeMillis() % 100000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                mcsLock.unlock(node);
            }
        };

        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            Thread.sleep(10);
        }
        Thread.sleep(30000);
    }



    /**
     * MCS锁本身不支持锁重入，如果需要支持，必须引入其他方式
     */
    public static class McsLock {
        private final AtomicReference<McsNode> tailNode = new AtomicReference<>();

        public McsNode lock() {
            McsNode mcsNode = new McsNode();
            mcsNode.locked = true;
            McsNode oldTailNode;
            do {
                oldTailNode = tailNode.get();
            } while (!tailNode.compareAndSet(oldTailNode, mcsNode));

            if (oldTailNode != null) {
                // 必须确保前驱节点的next指向当前节点
                oldTailNode.nextNode = mcsNode;
                // 自旋等待前驱节点释放锁
                while (mcsNode.locked) {}
            }
            return mcsNode;
        }

        public void unlock(McsNode node) {
            // 如果当前节点是最后一个节点，尝试将tail置为null
            if (tailNode.compareAndSet(node, null)) {
                return;
            }

            // 自旋等待后驱节点准确设置
            while (node.nextNode == null) {}

            // 唤醒后继节点
            node.nextNode.locked = false;
        }
    }

    public static class McsNode {
        private volatile boolean locked;
        private volatile McsNode nextNode;
    }
}
