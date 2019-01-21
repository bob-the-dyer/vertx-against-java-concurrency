package ru.spb.kupchinolab.vertx_against_java_concurrency.old_school.readers_writers;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Writer extends Player {

    public static AtomicInteger servedWriters = new AtomicInteger(0);

    public Writer(String name, Random random, int maxDelay, ReentrantReadWriteLock rwLock) {
        super(name, random, maxDelay, rwLock);
    }

    @Override
    public void play() {
        ReentrantReadWriteLock.WriteLock lock = rwLock.writeLock();
        lock.lock();
        try {
            super.play();
            servedWriters.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }

}
