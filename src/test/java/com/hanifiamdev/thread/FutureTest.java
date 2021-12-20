package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {

    @Test
    void testFuture() throws ExecutionException, InterruptedException {

        var executor = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
          Thread.sleep(5000);
          return "hi";
        };
        Future<String> future =  executor.submit(callable);
        System.out.println("selesai Future");

        while(!future.isDone()) {
            System.out.println("waiting future");
            Thread.sleep(1000);
        }
        String value = future.get();
        System.out.println(value);


    }

    @Test
    void testFutureCancel() throws ExecutionException, InterruptedException {

        var executor = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            Thread.sleep(5000);
            return "hi";
        };
        Future<String> future =  executor.submit(callable);
        System.out.println("selesai Future");

        Thread.sleep(2000); // setelah 2 detik
        future.cancel(true); // kirim sinyal cancel
        System.out.println("Apakah di cacel? " + future.isCancelled());
        String value = future.get();
        System.out.println(value);


    }

    @Test
    void invokeAll() throws ExecutionException, InterruptedException {
        var executor = Executors.newFixedThreadPool(10);
        // Jika menggunakan Anonymous class
       /* List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(operand -> new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(operand * 500L);
                return String.valueOf(operand);
            }
        }).collect(Collectors.toList());*/

        // jJika menggunakan full java stream
        List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(operand -> (Callable<String>) () -> {
            Thread.sleep(operand * 500L);
            return String.valueOf(operand);
        }).collect(Collectors.toList());

        var future = executor.invokeAll(callables);

        for (Future<String> stringFuture : future) {
            System.out.println(stringFuture.get());
        }
    }

    @Test
    void invokeAny() throws ExecutionException, InterruptedException {
        var executor = Executors.newFixedThreadPool(10);
        // Jika menggunakan Anonymous class
       /* List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(operand -> new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(operand * 500L);
                return String.valueOf(operand);
            }
        }).collect(Collectors.toList());*/

        // jJika menggunakan full java stream
        List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(operand -> (Callable<String>) () -> {
            Thread.sleep(operand * 500L);
            return String.valueOf(operand);
        }).collect(Collectors.toList());

        var value = executor.invokeAny(callables);
        System.out.println(value);
    }
}
