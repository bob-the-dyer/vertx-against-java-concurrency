package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;

import java.util.Random;

import static ru.spb.kupchinolab.vajc.readers_writers.Constants.TIME_TO_SLEEP_IN_MILLIS;

public class Player extends AbstractVerticle {

    private final String name;
    private final Random random;
    private final int maxDelay;

    Player(String name, Random random, int maxDelay) {
        this.name = name;
        this.random = random;
        this.maxDelay = maxDelay;
    }

    @Override
    public void start() {
        System.out.println(name + " started");
        vertx.setTimer(random.nextInt(maxDelay + 1), new PlayHandler());
    }

    protected void play() {
        try {
            Thread.sleep(TIME_TO_SLEEP_IN_MILLIS); // DON'T DO THIS EVER INSIDE EVENTLOOP!!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int nextDelay = random.nextInt(maxDelay + 1);
        //System.out.println(System.currentTimeMillis() + ": " + name + ": reader_locks = " + rwLock.getReadLockCount() + ", writer_lock = " + (rwLock.isWriteLocked() ? 1 : 0) + ", waiting_to_lock = " + rwLock.getQueueLength() + " , next run in " + nextDelay + " millis");
        System.out.println(System.currentTimeMillis() + ": " + name + ": next run in " + nextDelay + " millis");
        vertx.setTimer(nextDelay, new PlayHandler());
    }

    private class PlayHandler implements Handler<Long> {

        @Override
        public void handle(Long event) {
            play();

        }
    }

}
