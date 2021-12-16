package com.hanifiamdev.thread;

public class SynchronizedCounter {

    private Long value = 0L;

    public synchronized void increment() { value++; }
/*
    public synchronized void increment() { ini untuk schronized statrmrnt
        synchronized (this) {
            value++;
        }
    }*/

    public Long getValue() { return value; }

}
