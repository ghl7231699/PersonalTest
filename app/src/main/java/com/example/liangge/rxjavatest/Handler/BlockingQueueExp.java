package com.example.liangge.rxjavatest.Handler;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExp {
    public static void main(String[] args) {
        BlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }

    static class Producer implements Runnable {
        private final BlockingQueue queue;
        private int i;

        Producer(BlockingQueue q) {
            queue = q;
        }

        public void run() {
            try {
                while (true) {
                    queue.put(produce());
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        Object produce() {
            i++;
            return "this is " + i;
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue queue;

        Consumer(BlockingQueue q) {
            queue = q;
        }

        public void run() {
            try {
                while (true) {
                    consume(queue.take());
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        void consume(Object x) {
            System.out.println(x.toString());
        }
    }
}
