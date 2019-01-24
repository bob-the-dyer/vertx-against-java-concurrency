package ru.spb.kupchinolab.vajc.dining_philosophers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static ru.spb.kupchinolab.vajc.dining_philosophers.Utils.getRandomDrinkDelayInMillis;
import static ru.spb.kupchinolab.vajc.dining_philosophers.Utils.getRandomEatDelayInMillis;

class Philosopher extends Thread {

    private Logger log = LoggerFactory.getLogger(this.getName());

    final int order;
    final Chopstick leftChopstick;
    final Chopstick rightChopstick;
    final Map<Integer, Map<String, Integer>> stats;
    CountDownLatch latch;

    Philosopher(int order, Chopstick leftChopstick, Chopstick rightChopstick, Map<Integer, Map<String, Integer>> stats, CountDownLatch latch) {
        this.latch = latch;
        this.stats = stats;
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
        log.info("philosopher {} is starting", order);
        while (true) {
            try {
                Thread.sleep(getRandomDrinkDelayInMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                int eatTime = getRandomEatDelayInMillis();

                log.info("[eattime] philosopher {} is going to eat for {} millis", order, eatTime);
                try {
                    Thread.sleep(eatTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateStats(eatTime);

            } finally {
                log.info("[second_unlock] philosopher {} is about to unlock {} chopstick", order, secondChopstick.order);
                secondChopstick.unlock();
            }
        } finally {
            log.info("[first_unlock] philosopher {} is about to unlock {} chopstick", order, firstChopstick.order);
            firstChopstick.unlock();
        }
    }

    private void updateStats(int eatTime) {
        Map<String, Integer> newStat = new HashMap<>();
        newStat.put("totalTime", eatTime);
        newStat.put("count", 1);
        stats.merge(order, newStat, (oldStat, delta) -> {
            Map<String, Integer> mergedStat = new HashMap<>();
            mergedStat.put("totalTime", oldStat.get("totalTime") + delta.get("totalTime"));
            mergedStat.put("count", oldStat.get("count") + delta.get("count"));
            return mergedStat;
        });
    }
}
