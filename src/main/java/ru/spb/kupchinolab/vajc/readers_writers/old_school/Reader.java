package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader extends AbstractAccessor {

    static AtomicInteger servedReaders = new AtomicInteger(0);

    Reader(String name, int maxDelay, ReentrantReadWriteLock rwLock) {
        super(name, maxDelay, rwLock);
    }

    @Override
    public void access() {
        ReentrantReadWriteLock.ReadLock lock = rwLock.readLock();
        lock.lock();
        try {
            super.access();
            servedReaders.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }
}
