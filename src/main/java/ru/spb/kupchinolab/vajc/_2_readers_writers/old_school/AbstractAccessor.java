package ru.spb.kupchinolab.vajc._2_readers_writers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.spb.kupchinolab.vajc._2_readers_writers.Utils.log;

public abstract class AbstractAccessor extends Thread {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final String name;
    final ReentrantReadWriteLock rwLock;

    AbstractAccessor(String name, ReentrantReadWriteLock rwLock) {
        super(name);
        this.name = name;
        this.rwLock = rwLock;
    }

    @Override
    public void run() {
        log.info("{} started", name);
        for (; ; ) {
            try {
                Thread.sleep(getDelay()); //emulating random access
            } catch (InterruptedException e) {
                log.warn(e.getMessage(), e);
            }
            access();
        }
    }

    protected void access() {
        long activeStart = System.currentTimeMillis();
        while (System.currentTimeMillis() <= activeStart + getDelay()) {
            //DO NOTHING - emulating active work with resource
        }
        log(name, rwLock.getReadLockCount(), rwLock.isWriteLocked() ? 1 : 0, rwLock.getQueueLength(), getDelay());
    }

    abstract protected long getDelay();
}
