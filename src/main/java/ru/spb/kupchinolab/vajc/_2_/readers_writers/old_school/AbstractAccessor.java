package ru.spb.kupchinolab.vajc._2_.readers_writers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.spb.kupchinolab.vajc._2_.readers_writers.Utils.log;

public abstract class AbstractAccessor extends Thread {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final String name;
    final ReentrantReadWriteLock rwLock;
    private final CountDownLatch latch;

    AbstractAccessor(String name, ReentrantReadWriteLock rwLock, CountDownLatch latch) {
        super(name);
        this.name = name;
        this.rwLock = rwLock;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
