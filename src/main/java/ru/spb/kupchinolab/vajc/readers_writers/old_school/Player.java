package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.spb.kupchinolab.vajc.readers_writers.Constants.TIME_TO_SLEEP_IN_MILLIS;

public class Player extends Thread {

    private final String name;
    private final Random random;
    private final int maxDelay;
    final ReentrantReadWriteLock rwLock;

    Player(String name, Random random, int maxDelay, ReentrantReadWriteLock rwLock) {
        super(name);
        this.name = name;
        this.random = random;
        this.maxDelay = maxDelay;
        this.rwLock = rwLock;
    }

    @Override
    public void run() {
        System.out.println(name + " started");
        new Timer().schedule(new Task(), random.nextInt(maxDelay + 1));
    }

    protected void play() {
        try {
            Thread.sleep(TIME_TO_SLEEP_IN_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int nextDelay = random.nextInt(maxDelay + 1);
        System.out.println(System.currentTimeMillis() + ": " + name + ": reader_locks = " + rwLock.getReadLockCount() + ", writer_lock = " + (rwLock.isWriteLocked() ? 1 : 0) + ", waiting_to_lock = " + rwLock.getQueueLength() + " , next run in " + nextDelay + " millis");
        new Timer().schedule(new Task(), nextDelay);
    }

    private class Task extends TimerTask {

        @Override
        public void run() {
            play();
        }
    }
}
