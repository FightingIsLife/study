package com.jrymos.study.base;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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


    @Test
    public void testWaitAndNotify() {
        Thread thread = new Thread(() -> {
            synchronized (object) {
                while (true) {
                    System.out.println(Thread.currentThread().getName());
                    object.notify();
                    try {
                        object.wait();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        thread.start();
        synchronized (object) {
            while (true) {
                System.out.println(Thread.currentThread().getName());
                object.notify();
                try {
                    object.wait();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Test
    public void testProducerAndConsumer() throws InterruptedException {
        ArrayList<Product> queue = new ArrayList<>();
        Producer producer = new Producer(queue, 1);
        producer.start();
        Consumer consumer = new Consumer(queue);
        consumer.start();
        Thread.sleep(10000);
        producer.interrupt(); // 可以考虑设置一个标志来结束线程
        consumer.interrupt();
    }


    public class Producer extends Thread {

        private final List<Product> queue;
        private final int max;

        public Producer(List<Product> queue, int max) {
            this.queue = queue;
            this.max = max;
        }

        public void produce() throws InterruptedException {
            while (!isInterrupted()) {
                synchronized (queue) {
                    while (queue.size() == max) {
                        queue.wait(); // 等待直到有空间
                    }
                    queue.add(new Product());
                    queue.notifyAll(); // 通知消费者
                }
            }
        }

        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class Consumer extends Thread {
        private final List<Product> queue;


        public Consumer(List<Product> queue) {
            this.queue = queue;
        }

        public void consume() throws InterruptedException {
            while (!isInterrupted()) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        queue.wait();
                    }
                    Product product = queue.remove(0);
                    queue.notifyAll(); // 通知生产者
                    product.consume();
                }
            }
        }

        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class Product {
        private static final AtomicInteger atomicInteger = new AtomicInteger();

        private final int value;

        public Product() {
            value = atomicInteger.getAndIncrement();
            try {
                Thread.sleep(400); //模拟耗时
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("created product:" + value);
        }

        public void consume() {
            try {
                Thread.sleep(600); //模拟耗时
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("consumed product:" + value);
        }
    }
}

