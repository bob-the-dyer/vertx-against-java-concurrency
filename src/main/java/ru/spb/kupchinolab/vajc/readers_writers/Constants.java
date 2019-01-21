package ru.spb.kupchinolab.vajc.readers_writers;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class Constants {

    final static public int READER_MAX_DELAY = 1000;
    final static public int WRITER_MAX_DELAY = 2500;
    final static public int NUMBER_OF_READERS = 10;
    final static public int NUMBER_OF_WRITERS = 2;
    final static public int TIME_TO_RUN_IN_MILLIS = 10000;
    final static public int TIME_TO_SLEEP_IN_MILLIS = 100;

    public static void exitAfterDelay(Supplier<Integer> readerPlays, Supplier<Integer> writerPlays) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + ": stopping Main with " + "r-plays = " + readerPlays.get() + " and w-plays = " + writerPlays.get());
                System.exit(0);
            }
        }, TIME_TO_RUN_IN_MILLIS);
    }

}
