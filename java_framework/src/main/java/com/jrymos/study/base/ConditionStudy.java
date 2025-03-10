package com.jrymos.study.base;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 假设我们模拟一个简单的十字路口道路，假设是单向直行交叉，通常有两个灯：
 * 东西向车辆直行灯
 * 南北向车辆直行灯
 */
public class ConditionStudy {

    ReentrantLock lock = new ReentrantLock(true);
    Condition ewCondition = lock.newCondition();
    Condition nsCondition = lock.newCondition();
    /**
     * 0 东西红灯， 南北红灯
     * 1 东西绿灯，南北红灯
     * 2 东西红灯，南北红灯
     * 3 东西红灯，南北绿灯
     */
    volatile int lightType = 0;

    @Test
    public void demo() throws InterruptedException {
        ReentrantLock notifyLock = new ReentrantLock(true);
        Condition notifyCondition = notifyLock.newCondition();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //时钟线程
        Thread lightTimeThread = new Thread(() -> {
            while (true) {
                countDownLatch.countDown();
                if (lightType == 0 || lightType == 2) {
                    mysleep(30);
                } else {
                    mysleep(100);
                }
                lightType = (lightType + 1) % 4;
                if (lightType == 1 ||lightType == 3) {
                    boolean lock = notifyLock.tryLock();
                    if (lock) {
                        try {
                            notifyCondition.signal();
                        } finally {
                            notifyLock.unlock();
                        }
                    }
                }
            }
        });

        //通知线程
        Thread notifyThread = new Thread(() -> {
            notifyLock.lock();
            try {
                while (true) {
                    try {
                        notifyCondition.await();
                    } catch (InterruptedException e) {
                    }
                    lock.lock();
                    try {
                        if (lightType == 1) {
                            ewCondition.signalAll(); // 东西向绿灯
                        }
                        if (lightType == 3) {
                            nsCondition.signalAll(); // 南北向绿灯
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            } finally {
                notifyLock.unlock();
            }
        });
        lightTimeThread.start();
        notifyThread.start();

        countDownLatch.await();

        for (int i = 0; i < 100; i++) {
            new Thread(new Car()).start();
        }
        Thread.sleep(10000);
    }

    private void mysleep(int i) {
        long startTime = System.currentTimeMillis();
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }
            if (System.currentTimeMillis() - startTime >= i) {
                return;
            }
        }
    }

    public class Car implements Runnable {

        public static AtomicInteger value = new AtomicInteger();

        public void run() {
            lock.lock();
            //验证算法公平
            int code = value.getAndIncrement();
            boolean ew = code % 2 == 0;
            try {
                while (lightType != (ew ? 1 : 3)) {
                    (ew ? ewCondition : nsCondition).await();
                }
                long millis = 10 + (System.currentTimeMillis() % 40);
                Thread.sleep(millis); // 模拟通行时间
                // 车辆通行
                System.out.println("car:" + code + "\t" + (ew ? "东->西" : "南->北") + "\tpass:" + millis + "ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}