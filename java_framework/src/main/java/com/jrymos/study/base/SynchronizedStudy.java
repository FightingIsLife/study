package com.jrymos.study.base;

import org.junit.Test;

public class SynchronizedStudy {

    private final Object object = new Object();

    @Test
    public void testLockClean() {
        synchronized (new Object()) {
            System.out.println("hello");
        }
    }

    @Test
    public void testLockGrow() {
        synchronized (object) {
            System.out.println();
        }

        System.out.println();

        synchronized (object) {
            System.out.println();
        }

        synchronized (object) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }
}
