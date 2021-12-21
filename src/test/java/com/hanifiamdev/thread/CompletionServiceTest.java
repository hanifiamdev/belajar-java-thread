package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceTest {

    private Random random = new Random();

    @Test
    void test() throws InterruptedException {
        var executor = Executors.newFixedThreadPool(10);
        // Ini sebagai jembatan siapa yang subnit task dan siapa yang pool task
        var completionService = new ExecutorCompletionService<String>(executor);
        // Submit task
        Executors.newSingleThreadExecutor().execute(() -> {
            for (int i = 0; i < 100; i++) {
                final var index  = i;
                completionService.submit(() -> {
                   Thread.sleep(random.nextInt(2000));
                   return "Task-" + index;
                });
            }
        });
        // poll task
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true){
                try {
                   Future<String> future =  completionService.poll(5, TimeUnit.SECONDS);
                   if(future == null) {
                       break;
                   }else {
                       System.out.println(future.get());
                   }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        /*
        * Pengirim submit (pool) dan penerima ( submit )
        * terletak di thread yang berbeda
        * */
        //executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}