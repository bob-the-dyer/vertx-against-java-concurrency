package ru.spb.kupchinolab.vajc.dining_philosophers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spb.kupchinolab.vajc.dining_philosophers.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc.dining_philosophers.Utils.MAX_TIME_TO_EAT_IN_MILLIS;
import static ru.spb.kupchinolab.vajc.dining_philosophers.Utils.PHILOSOPHERS_COUNT;

public class Main {

    static Map stats = new ConcurrentHashMap();

    public static void main(String[] args) {

        List<Chopstick> chopsticks = new ArrayList<>();

        IntStream.range(0, PHILOSOPHERS_COUNT).forEach(i -> {
            Chopstick f = new Chopstick(i);
            chopsticks.add(f);
        });

        CountDownLatch latch = new CountDownLatch(1);

        IntStream.range(0, PHILOSOPHERS_COUNT).forEach(i -> {
            Philosopher p = new Philosopher(latch, i, chopsticks.get(i), chopsticks.get(i != 0 ? i - 1 : PHILOSOPHERS_COUNT - 1));
            p.start();
        });

        latch.countDown();

        Utils.exitAfterDelay(() -> stats);
    }

    static class Philosopher extends Thread {

        private Logger log = LoggerFactory.getLogger(this.getName());

        CountDownLatch latch;

        final int order;
        final Chopstick leftChopstick;
        final Chopstick rightChopstick;

        Philosopher(CountDownLatch latch, int order, Chopstick leftChopstick, Chopstick rightChopstick) {
            this.latch = latch;
            this.order = order;
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                dinner();
            }
        }

        private void dinner() {
            Chopstick firstChopstick;
            Chopstick secondChopstick;
            if (rightChopstick.order < leftChopstick.order) {
                assert order != 0;
                firstChopstick = rightChopstick;
                secondChopstick = leftChopstick;
            } else { // leftChopstick.order < rightChopstick.order
                assert order == 0;
                firstChopstick = leftChopstick;
                secondChopstick = rightChopstick;
            }
            log.info("[first_lock] philosopher {} try to take {} chopstick", order, firstChopstick.order);
            firstChopstick.lock();
            try {
                log.info("[second_lock] philosopher {} try to take {} chopstick", order, secondChopstick.order);
                secondChopstick.lock();
                try {
                    int eatTime = ThreadLocalRandom.current().nextInt(1, MAX_TIME_TO_EAT_IN_MILLIS);

                    log.info("[eattime] philosopher {} is going to eat for {} millis", order, eatTime);
                    try {
                        Thread.sleep(eatTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    stats.merge(order, eatTime, (BiFunction<Integer, Integer, Integer>) (oldTotal, newTotal) -> (oldTotal + newTotal));
                } finally {
                    secondChopstick.unlock();
                    log.info("[second_unlock] philosopher {} unlocked {} chopstick", order, secondChopstick.order);
                }
            } finally {
                firstChopstick.unlock();
                log.info("[first_unlock] philosopher {} unlocked {} chopstick", order, firstChopstick.order);
            }
        }
    }

    static class Chopstick extends ReentrantLock {

        final int order;

        Chopstick(int order) {
            this.order = order;
        }
    }
}
