package ru.spb.kupchinolab.vajc._1_.producer_consumer.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc._1_.producer_consumer.Utils.getRandomProduceTimeInMillis;

public class Producer extends AbstractVerticle {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    int producedCount = 0;

    @Override
    public void start() {
        int delay = getRandomProduceTimeInMillis();
        log.info("producer is going to send message in {} millis", delay);
        vertx.setTimer(delay, new TimerHandler(vertx));
    }

    class TimerHandler implements Handler<Long> {

        private Vertx vertx;

        TimerHandler(Vertx vertx) {
            this.vertx = vertx;
        }

        @Override
        public void handle(Long timerId) {
            producedCount++;
            String message = "I like HighLoad! " + producedCount;
            log.info("producer is sending message '{}'", message);
            vertx.eventBus().send("production", message, reply -> {
                int delay = getRandomProduceTimeInMillis();
                log.info("producer is going to send next message in {} millis", delay);
                vertx.setTimer(delay, new TimerHandler(vertx));
            });
        }
    }
}
