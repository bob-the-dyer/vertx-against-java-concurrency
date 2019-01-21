package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Writer extends Player {

    static AtomicInteger servedWriters = new AtomicInteger(0);

    Writer(String name, Random random, int maxDelay, ReentrantReadWriteLock rwLock) {
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
