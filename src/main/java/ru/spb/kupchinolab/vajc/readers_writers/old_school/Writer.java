package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Writer extends AbstractAccessor {

    static AtomicInteger servedWriters = new AtomicInteger(0);

    Writer(String name, int maxDelay, ReentrantReadWriteLock rwLock) {
        super(name, maxDelay, rwLock);
    }

    @Override
    public void access() {
        ReentrantReadWriteLock.WriteLock lock = rwLock.writeLock();
        lock.lock();
        try {
            super.access();
            servedWriters.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }

}
