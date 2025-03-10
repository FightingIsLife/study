package com.jrymos.study.base;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockStudy {

    private volatile int demoValue = 0;
    private final ReentrantLock demoValueModifyReentrantLock = new ReentrantLock();

    public void demoValueIncrease() {
        try {
            demoValueModifyReentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + "\t" + demoValue++);
        } finally {
            demoValueModifyReentrantLock.unlock();
        }
    }

    @Test
    public void testDemoValue() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                demoValueIncrease();
            }
        }, "test");
        thread.start();
        for (int i = 0; i < 1000; i++) {
            demoValueIncrease();
        }
        Thread.sleep(1000);
        System.out.println(demoValue);
    }
}
