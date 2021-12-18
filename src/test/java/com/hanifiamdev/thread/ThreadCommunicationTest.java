package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

public class ThreadCommunicationTest {

    private String message = null;

    @Test
    void manual() throws InterruptedException {

        var thread1 = new Thread(() -> {
            while (message == null) {

            }
            System.out.println(message);
        });

        var thread2 = new Thread(() -> {
           message = "Hanif Amrullah";
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    @Test
    void waitNotify() throws InterruptedException {
        final var lock = new Object();

        var thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {

                }
            }
        });

        var thread2 = new Thread(() -> {
            synchronized (lock) {
                message = "Hanif Amrullah";
                lock.notify();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
    @Test
    void waitNotifyAll() throws InterruptedException {
        final var lock = new Object();

        var thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {

                }
            }
        });

        var thread2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {

                }
            }
        });

        var thread3 = new Thread(() -> {
            synchronized (lock) {
                message = "Hanif Amrullah";
                lock.notifyAll();
            }
        });


        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
    }

}
