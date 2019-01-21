package ru.spb.kupchinolab.vertx_against_java_concurrency.old_school.readers_writers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() + ": starting Main");
        Random random = new Random();

        //the following combination leads to almost 1/10 proportion
        int readerMaxDelay = 1000;
        int writerMaxDelay = 2500;
        int numberOfReaders = 10;
        int numberOfWriters = 2;
        int totalTimeToRunInMillis = 10000;

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        //start readers
        IntStream.range(1, numberOfReaders + 1)
                .forEach(i -> new Reader("reader " + i, random, readerMaxDelay, rwLock).start());

        //start writers
        IntStream.range(1, numberOfWriters + 1)
                .forEach(i -> new Writer("writer " + i, random, writerMaxDelay, rwLock).start());

        exitAfterMillis(totalTimeToRunInMillis);
    }

    private static void exitAfterMillis(long millis) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + ": stopping Main with " + "readers = " + Reader.servedReaders.get() + " and writers = " + Writer.servedWriters.get());
                System.exit(0);
            }
        }, millis);
    }
}
