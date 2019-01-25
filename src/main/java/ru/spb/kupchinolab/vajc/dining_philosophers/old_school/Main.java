package ru.spb.kupchinolab.vajc.dining_philosophers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spb.kupchinolab.vajc.dining_philosophers.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc.dining_philosophers.Utils.PHILOSOPHERS_COUNT;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {

        log.info("starting Main");

        List<Chopstick> chopsticks = new ArrayList<>();

        IntStream.range(0, PHILOSOPHERS_COUNT).forEach(i -> {
            Chopstick f = new Chopstick(i);
            chopsticks.add(f);
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

        Utils.exitAfterDelay(() -> stats);
    }

}
