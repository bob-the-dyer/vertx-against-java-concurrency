package ru.spb.kupchinolab.vertx_against_java_concurrency.old_school.readers_writers;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader extends Player {

    public static AtomicInteger servedReaders = new AtomicInteger(0);

    public Reader(String name, Random random, int maxDelay, ReentrantReadWriteLock rwLock) {
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
