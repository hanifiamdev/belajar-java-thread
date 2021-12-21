package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private Random random = new Random();

    public Future<String> getValue() {
        CompletableFuture<String> future = new CompletableFuture<>();

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                future.complete("Done");
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    @Test
    void create() throws ExecutionException, InterruptedException {
        Future<String> future = getValue();
        System.out.println(future.get());
    }

    private void execute(CompletableFuture<String> future, String value) {
        executorService.execute(() -> {
            try {
                Thread.sleep(1000 + random.nextInt(5000));
                future.complete(value);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
    }

    public Future<String> getFastest() {
        CompletableFuture<String> future = new CompletableFuture<>();

        execute(future, "Thread 1");
        execute(future, "Thread 2");
        execute(future, "Thread 3");

        return future;
    };

    @Test
    void testFastest() throws ExecutionException, InterruptedException {
        // Hssil akan selalu berubah, dimana menentukan proses yang tercepet
        System.out.println(getFastest().get());
    }

    public CompletableFuture<String> getValueV2() {
        CompletableFuture<String> future = new CompletableFuture<>();

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                future.complete("Hanif Amrullah");
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    @Test
    void testCompletionStage() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = getValueV2();

        CompletableFuture<String[]> future2 = future.thenApply(string -> string.toUpperCase())
                .thenApply(string -> string.split(" "));

        String[] strings = future2.get();
        for (var value : strings) {
            System.out.println(value);
        }
    }
}
