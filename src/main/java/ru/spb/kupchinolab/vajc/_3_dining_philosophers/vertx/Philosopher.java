package ru.spb.kupchinolab.vajc._3_dining_philosophers.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.Lock;
import io.vertx.core.shareddata.SharedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

import static ru.spb.kupchinolab.vajc._3_dining_philosophers.Utils.*;

public class Philosopher extends AbstractVerticle {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    static int globalOrder = 0;

    private final int order;
    private final int firstChopstick;
    private final int secondChopstick;

    public Philosopher() {
        this.order = globalOrder;
        globalOrder++;
        if (order == 0) {
            firstChopstick = order;
            secondChopstick = (PHILOSOPHERS_COUNT - 1);
        } else {
            firstChopstick = (order - 1);
            secondChopstick = order;
        }
        log.info("philosopher {} was instantiated with chopsticks {} and {} ", order, firstChopstick, secondChopstick);
    }

    @Override
    public void start() {
        vertx.eventBus().consumer("start_topic", event -> {
            log.info("philosopher {} is starting", order);
            boolean skipDrink = ThreadLocalRandom.current().nextBoolean();
            if (skipDrink) {
                eat();
            } else {
                vertx.setTimer(getRandomDrinkDelayInMillis(), new AttemptToEat());
            }
        });
    }

    private void eat() {
        SharedData sharedData = vertx.sharedData();
        log.info("[first_lock] philosopher {} try to take {} chopstick", order, firstChopstick);
        sharedData.getLock("chopstick_" + firstChopstick, event1 -> {
            if (event1.succeeded()) {
                Lock chopstick1 = event1.result();

                log.info("[second_lock] philosopher {} try to take {} chopstick", order, secondChopstick);
                sharedData.getLock("chopstick_" + secondChopstick, event2 -> {
                    if (event2.succeeded()) {
                        Lock chopstick2 = event2.result();

                        int eatTime = getRandomEatDelayInMillis();
                        log.info("[eattime] philosopher {} is going to eat for {} millis", order, eatTime);

                        long startTime = System.currentTimeMillis();
                        while (System.currentTimeMillis() < startTime + eatTime) ;

                        updateStats(eatTime);

                        log.info("[second_unlock] philosopher {} is about to unlock {} chopstick", order, secondChopstick);
                        chopstick2.release();
                    } else {
                        log.warn("second chopstick {} can't be held by philosopher {} because of {} ", secondChopstick, order, event2.cause().getMessage());
                    }

                    log.info("[first_unlock] philosopher {} is about to unlock {} chopstick", order, firstChopstick);
                    chopstick1.release();
                    vertx.setTimer(getRandomDrinkDelayInMillis(), new AttemptToEat());
                });
            } else {
                log.warn("first chopstick {} can't be held by philosopher {} because of '{}' ", firstChopstick, order, event1.cause().getMessage());
                vertx.setTimer(getRandomDrinkDelayInMillis(), new AttemptToEat());
            }
        });
    }

    private void updateStats(int eatTime) {
        JsonObject stats = new JsonObject()
                .put("order", order)
                .put("eatTime", eatTime)
                .put("count", 1);
        vertx.eventBus().send("usage_statistics", stats);
    }

    class AttemptToEat implements Handler<Long> {

        @Override
        public void handle(Long timerId) {
            eat();
        }
    }
}
