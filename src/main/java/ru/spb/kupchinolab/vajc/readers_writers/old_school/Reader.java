package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader extends Player {

    static AtomicInteger servedReaders = new AtomicInteger(0);

    Reader(String name, Random random, int maxDelay, ReentrantReadWriteLock rwLock) {
        super(name, random, maxDelay, rwLock);
    }

    @Override
    public void play() {
        ReentrantReadWriteLock.ReadLock lock = rwLock.readLock();
        lock.lock();
        try {
            super.play();
            servedReaders.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }
}
