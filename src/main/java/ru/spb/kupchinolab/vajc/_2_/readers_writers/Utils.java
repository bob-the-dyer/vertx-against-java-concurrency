package ru.spb.kupchinolab.vajc._2_.readers_writers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class.getName());

    final static public int NUMBER_OF_READERS = 8;
    final static public int NUMBER_OF_WRITERS = 2;
    private final static int TIME_TO_RUN_IN_MILLIS = 10000;
    private final static int MAX_TIME_TO_READ_IN_MILLIS = 50;
    private final static int MAX_TIME_TO_WRITE_IN_MILLIS = 50;

    public static void exitAfterDelay(Supplier<Integer> readerAccessCount, Supplier<Integer> writerAccessCount) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("stopping Main with read-accesses = {} and write-accesses = {}",
                        readerAccessCount.get(), writerAccessCount.get());
                System.exit(0);
            }
        }, TIME_TO_RUN_IN_MILLIS);
    }

    public static void log(String name, int currentReadersCount, int currentWritersCount, int accessorsAwaitingResource, long nextDelay) {
        log.info("[{}] reader_locks = {}, writer_lock = {}, waiting_to_lock = {}, next run in {} millis",
                name, currentReadersCount, currentWritersCount, accessorsAwaitingResource, nextDelay);
    }

    public static int getRandomReadDelayInMillis() {
        return ThreadLocalRandom.current().nextInt(1, MAX_TIME_TO_READ_IN_MILLIS);
    }

    public static int getRandomWriteDelayInMillis() {
        return ThreadLocalRandom.current().nextInt(1, MAX_TIME_TO_WRITE_IN_MILLIS);
    }

}
