package com.jrymos.study.base;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadStudy {

    @Test
    public void threadStateStudy() {
        Object blocked = new Object();
        Thread otherTestThread = new Thread(() -> sleep(99999));
        otherTestThread.start();

        Thread testThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getState() + "\t 2t");
            try {
                Thread.sleep(10000);// 3.进入：TIMED_WAITING
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getState() + "\t 4t");
            }

            try {
                otherTestThread.join(); // 5.进入：WAITING，或者object.wait() ，等待object.notify()
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getState() + "\t 6t");
            //7.进入 BLOCKED
            synchronized (blocked) {
                System.out.println(Thread.currentThread().getState() + "\t 8t");
            }
        });
        System.out.println(testThread.getState() + "\t 1"); //NEW
        testThread.start();
        sleep(10);
        System.out.println(testThread.getState() + "\t 3"); //TIMED_WAITING
        testThread.interrupt();
        sleep(10);
        System.out.println(testThread.getState() + "\t 5"); //WAITING
        synchronized (blocked) {
            otherTestThread.interrupt();
            sleep(10);
            System.out.println(testThread.getState() + "\t 7"); //BLOCKED
        }
        sleep(10);
        System.out.println(testThread.getState() + "\t 9"); //TERMINATED
    }



    @Test
    public void createThreadStudy() throws InterruptedException, ExecutionException, TimeoutException {
        Thread thread1 = createAndStartThread1();

        Thread thread2 = createAndStartThread2();

        Future<String> future = createAndStartThread3();
        System.out.println(future.get(1, TimeUnit.SECONDS));
        sleep(20);
        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
    }

    private RunnableFuture<String> createAndStartThread3() {
        Callable<String> callable = () -> "thread3 run";
        RunnableFuture<String> runnableFuture = new FutureTask<>(callable);
        new Thread(runnableFuture).start();
        return runnableFuture;
    }

    private Thread createAndStartThread2() {
        Thread thread = new Thread(() -> {
            sleep(10);
            System.out.println("thread2 run");
        });
        thread.start();
        return thread;
    }

    private Thread createAndStartThread1() {
        //创建线程1
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread1 run");
            }
        };
        thread.start();
        return thread;
    }

    public void sleep(long value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            return;
        }
    }
}
