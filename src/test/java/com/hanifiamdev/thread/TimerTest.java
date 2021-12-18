package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    void delayedJob() throws InterruptedException {

        var task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        var timer = new Timer();
        timer.schedule(task, 2000); // delay 2 detik

        Thread.sleep(3000); // ager menyelesaikan proses setelah 3 detik
    }

    @Test
    void periodicJob() throws InterruptedException {

        var task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        var timer = new Timer();
        timer.schedule(task, 2000,2000); // delay 2 detik dan akan diulang setiap 2 detik sekali

        Thread.sleep(10_000); // ager menyelesaikan proses setelah 10 detik
    }
}
