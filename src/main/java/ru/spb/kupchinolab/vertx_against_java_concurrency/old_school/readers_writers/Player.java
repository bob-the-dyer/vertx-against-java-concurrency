package ru.spb.kupchinolab.vertx_against_java_concurrency.old_school.readers_writers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Player extends Thread {

    protected final String name;
    private final Random random;
    private final int maxDelay;
    protected final ReentrantReadWriteLock rwLock;

    public Player(String name, Random random, int maxDelay, ReentrantReadWriteLock rwLock) {
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
        int nextDelay = random.nextInt(maxDelay + 1);
        System.out.println(System.currentTimeMillis() + ": " + name + ": readers = " + rwLock.getReadLockCount() + ", writer = " + rwLock.isWriteLocked()+ ", waiting = " + rwLock.getQueueLength() + " , next run in " + nextDelay + " millis");
        new Timer().schedule(new Task(), nextDelay);
    }

    private class Task extends TimerTask {

        @Override
        public void run() {
            play();
        }
    }
}
