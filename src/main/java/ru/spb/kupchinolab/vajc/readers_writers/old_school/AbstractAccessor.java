package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.TIME_TO_SLEEP_IN_MILLIS;
import static ru.spb.kupchinolab.vajc.readers_writers.Utils.log;

public abstract class AbstractAccessor extends Thread {

    private final String name;
    private final int maxDelay;
    final ReentrantReadWriteLock rwLock;

    AbstractAccessor(String name, int maxDelay, ReentrantReadWriteLock rwLock) {
        super(name);
        this.name = name;
        this.maxDelay = maxDelay;
        this.rwLock = rwLock;
    }

    @Override
    public void run() {
        System.out.println(name + " started");
        new Timer().schedule(new AccessTask(), ThreadLocalRandom.current().nextInt(1, maxDelay));
    }

    protected void access() {
        try {
            Thread.sleep(TIME_TO_SLEEP_IN_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int nextDelay = ThreadLocalRandom.current().nextInt(1, maxDelay);
        log(name, rwLock.getReadLockCount(), rwLock.isWriteLocked() ? 1 : 0, rwLock.getQueueLength(), nextDelay);
        new Timer().schedule(new AccessTask(), nextDelay);
    }

    private class AccessTask extends TimerTask {

        @Override
        public void run() {
            access();
        }
    }
}
