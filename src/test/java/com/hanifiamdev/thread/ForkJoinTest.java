package com.hanifiamdev.thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForkJoinTest {

    @Test
    void create() {

        var forkJoinPool1 = ForkJoinPool.commonPool(); // thread dengan total core
        var forkJoinPool2 = new ForkJoinPool(5); // thread dengan 5 core

        var executor1 = Executors.newWorkStealingPool(); // thread dengan total core
        var executor2 = Executors.newWorkStealingPool(5); // thread dengan 5 core
    }

    @Test
    void recursiveAction() throws InterruptedException {

        var pool = ForkJoinPool.commonPool();
        List<Integer> integers = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

        var task = new SimpleForkJoinTask(integers);
        pool.execute(task);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);

    }

    public static class SimpleForkJoinTask extends RecursiveAction {

        private List<Integer> integers;

        public SimpleForkJoinTask(List<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected void compute() {
            if(integers.size() < 10) {
                doExecute();
            } else {
                forkCompute();
            }

        }

        private void doExecute() {
            integers.forEach(integer -> {
                System.out.println(Thread.currentThread().getName() + " : " + integer);
            });
        }

        private void forkCompute() {
            List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
            List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

            SimpleForkJoinTask task1 = new SimpleForkJoinTask(integers1);
            SimpleForkJoinTask task2 = new SimpleForkJoinTask(integers2);

            ForkJoinTask.invokeAll(task1, task2);
        }


    }

    @Test
    void recursiveTask() throws InterruptedException, ExecutionException {
        var pool = ForkJoinPool.commonPool();
        List<Integer> integers = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

        var task = new TotalRecursiveTask(integers);
        ForkJoinTask<Long> submit = pool.submit(task);

        Long aLong = submit.get();
        System.out.println(aLong);

        // Ini hanya untuk check apakah RecursiveTask mengembalikan perthitungan sum dengan benar
        long sum  = integers.stream().mapToLong(value -> value).sum();
        System.out.println(sum);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
    }

    public static class TotalRecursiveTask extends RecursiveTask<Long> {

        private List<Integer> integers;

        public TotalRecursiveTask(List<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected Long compute() {
            if(integers.size() < 10) {
                return doCompute();
            }else{
                return forkCompute();
            }
        }

        private Long forkCompute() {
            List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
            List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

            TotalRecursiveTask task1 = new TotalRecursiveTask(integers1);
            TotalRecursiveTask task2 = new TotalRecursiveTask(integers2);

            ForkJoinTask.invokeAll(task1, task2);
            return task1.join() + task2.join();
        }

        private Long doCompute() {
            return integers.stream().mapToLong(value -> value).peek(value -> {
                System.out.println(Thread.currentThread().getName() + " : " + value);
            }).sum();
        }
    }
}
