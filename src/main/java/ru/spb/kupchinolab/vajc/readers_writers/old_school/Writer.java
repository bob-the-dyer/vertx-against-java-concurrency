package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.getRandomWriteDelayInMillis;

public class Writer extends AbstractAccessor {

    static AtomicInteger servedWriters = new AtomicInteger(0);

    Writer(String name, ReentrantReadWriteLock rwLock) {
        super(name, rwLock);
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

    @Override
    protected long getDelay() {
        return getRandomWriteDelayInMillis();
    }

}
