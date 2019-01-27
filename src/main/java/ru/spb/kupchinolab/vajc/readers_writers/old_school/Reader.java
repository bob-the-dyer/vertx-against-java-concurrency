package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.getRandomReadDelayInMillis;

public class Reader extends AbstractAccessor {

    static AtomicInteger servedReaders = new AtomicInteger(0);

    Reader(String name, ReentrantReadWriteLock rwLock) {
        super(name, rwLock);
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

    @Override
    protected long getDelay() {
        return getRandomReadDelayInMillis();
    }
}
