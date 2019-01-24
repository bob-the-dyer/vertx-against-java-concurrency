package ru.spb.kupchinolab.vajc.dining_philosophers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class.getName());

    private final static int TIME_TO_RUN_IN_MILLIS = 10000;
    public final static int PHILOSOPHERS_COUNT = 5;
    public final static int MAX_TIME_TO_EAT_IN_MILLIS = 50;
    public final static int MAX_TIME_TO_DRINK_IN_MILLIS = 50;

    public static void exitAfterDelay(Supplier<Map<Integer, Map<String, Integer>>> stats) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Map<Integer, Map<String, Integer>> statsMap = stats.get();
                int overallEatTime = statsMap.values().stream().mapToInt(t -> t.get("totalTime")).sum();
                int overallEatAttempts = statsMap.values().stream().mapToInt(t -> t.get("count")).sum();
                log.info("stopping Main with overall eat time = {} and overall eat attempts {}, detailed stats: {}",
                        overallEatTime, overallEatAttempts, statsMap);
                System.exit(0);
            }
        }, TIME_TO_RUN_IN_MILLIS);
    }

    public static int getRandomDrinkDelayInMillis() {
        return ThreadLocalRandom.current().nextInt(1, MAX_TIME_TO_DRINK_IN_MILLIS);
    }

    public static int getRandomEatDelayInMillis() {
        return ThreadLocalRandom.current().nextInt(1, MAX_TIME_TO_EAT_IN_MILLIS);
    }

}
