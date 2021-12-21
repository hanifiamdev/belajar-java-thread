package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {


    @Test
    void test() {

        final var cyclicBarrier  = new CyclicBarrier(5);
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    System.out.println("Prose ke-" + index );
                    cyclicBarrier.await(); // nunggu 5 thread baru akan dilepas
                    System.out.println("Done Waiting");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
