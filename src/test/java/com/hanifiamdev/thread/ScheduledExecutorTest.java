package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest {

    @Test
    void deleyedjob() throws InterruptedException {

        var executor = Executors.newScheduledThreadPool(10);

        var future = executor.schedule(() -> System.out.println("Hanif Amrullah"), 5, TimeUnit.SECONDS);

        System.out.println("Waktu Delay Task yang tersisa : " + future.getDelay(TimeUnit.MILLISECONDS) + "ms");

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void periodicjob() throws InterruptedException {

        var executor = Executors.newScheduledThreadPool(10);

        var future = executor.scheduleAtFixedRate(() -> System.out.println("Hanif Amrullah"), 2, 2, TimeUnit.SECONDS);

        System.out.println("Waktu Delay Task yang tersisa : " + future.getDelay(TimeUnit.MILLISECONDS) + "ms");

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
