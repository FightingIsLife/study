package com.jrymos.study.base;

import org.junit.Test;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * aqs核心字段：
 * <pre>
 * volatile int state; //The synchronization state.
 * volatile Node head; //线程等待队列，头节点
 * volatile Node tail; //线程等待队列，尾节点
 * </pre>
 */
public class AqsLockStudy {

    @Test
    public void test() {
        System.out.println(~2);
        System.out.println(~-3);
    }


    public static class MyAqsLock extends AbstractQueuedSynchronizer {

    }
}
