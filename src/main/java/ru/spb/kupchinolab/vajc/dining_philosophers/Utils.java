package ru.spb.kupchinolab.vajc.dining_philosophers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class.getName());

    private final static int TIME_TO_RUN_IN_MILLIS = 10000;
    public final static int PHILOSOPHERS_COUNT = 5;
    public final static int MAX_TIME_TO_EAT_IN_MILLIS = 50;

    public static void exitAfterDelay(Supplier<Map<Integer, Map<String, Integer>>> stats) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("stopping Main with the following stats: {}", stats.get());
                System.exit(0);
            }
        }, TIME_TO_RUN_IN_MILLIS);
    }

}
