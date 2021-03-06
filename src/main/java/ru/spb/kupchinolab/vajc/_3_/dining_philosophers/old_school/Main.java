package ru.spb.kupchinolab.vajc._3_.dining_philosophers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc._3_.dining_philosophers.Utils.PHILOSOPHERS_COUNT;
import static ru.spb.kupchinolab.vajc._3_.dining_philosophers.Utils.exitAfterDelay;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {

        log.info("starting Main");

        List<Chopstick> chopsticks = new ArrayList<>();

        IntStream.range(0, PHILOSOPHERS_COUNT).forEach(i -> {
            Chopstick cs = new Chopstick(i);
            chopsticks.add(cs);
        });

        CountDownLatch latch = new CountDownLatch(1);

        Map<Integer, Map<String, Integer>> stats = new ConcurrentHashMap<>();

        IntStream.range(0, PHILOSOPHERS_COUNT).forEach(i -> {
            Chopstick leftChopstick = chopsticks.get(i);
            Chopstick rightChopstick = chopsticks.get(i != 0 ? i - 1 : PHILOSOPHERS_COUNT - 1);
            Philosopher p = new Philosopher(i, leftChopstick, rightChopstick, stats, latch);
            p.start();
        });

        latch.countDown();

        exitAfterDelay(() -> stats);
    }

}
