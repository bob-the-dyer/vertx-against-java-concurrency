package ru.spb.kupchinolab.vajc.readers_writers;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class Utils {

    final static public int READER_MAX_DELAY = 1000;
    final static public int WRITER_MAX_DELAY = 2500;
    final static public int NUMBER_OF_READERS = 10;
    final static public int NUMBER_OF_WRITERS = 2;
    final static public int TIME_TO_RUN_IN_MILLIS = 60000;
    final static public int TIME_TO_SLEEP_IN_MILLIS = 100;

    public static void exitAfterDelay(Supplier<Integer> readerAccessCount, Supplier<Integer> writerAccessCount) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + ": stopping Main with " + "read-accesses = " + readerAccessCount.get() + " and write-accesses = " + writerAccessCount.get());
                System.exit(0);
            }
        }, TIME_TO_RUN_IN_MILLIS);
    }

    public static void log(String name, int currentReadersCount, int currentWritersCount, int accessorsAwaitingResource, int nextDelay) {
        System.out.println(System.currentTimeMillis() + " [" + name + "] reader_locks = " + currentReadersCount
                + ", writer_lock = " + currentWritersCount
                + ", waiting_to_lock = " + accessorsAwaitingResource
                + ", next run in " + nextDelay + " millis");
    }

}
