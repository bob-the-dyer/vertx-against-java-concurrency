package ru.spb.kupchinolab.vajc._1_.producer_consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class.getName());

    public final static int BUFFER_CAPACITY = 5;
    private final static int TIME_TO_RUN_IN_MILLIS = 10000;
    private final static int MAX_TIME_TO_CONSUME_IN_MILLIS = 40;
    private final static int MAX_TIME_TO_PRODUCE_IN_MILLIS = 20;

    public static int getRandomConsumeTimeInMillis() {
        return ThreadLocalRandom.current().nextInt(1, MAX_TIME_TO_CONSUME_IN_MILLIS);
    }

    public static int getRandomProduceTimeInMillis() {
        return ThreadLocalRandom.current().nextInt(1, MAX_TIME_TO_PRODUCE_IN_MILLIS);
    }

    public static void exitAfterDelay(Supplier<Integer> consumed, Supplier<Integer> produced) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("stopping Main, consumed {}, produced {} messages", consumed.get(), produced.get());
                System.exit(0);
            }
        }, TIME_TO_RUN_IN_MILLIS);
    }
}
