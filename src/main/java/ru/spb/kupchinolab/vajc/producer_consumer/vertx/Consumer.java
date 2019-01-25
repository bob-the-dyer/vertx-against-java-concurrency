package ru.spb.kupchinolab.vajc.producer_consumer.vertx;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc.producer_consumer.Utils.getRandomConsumeTimeInMillis;

public class Consumer extends AbstractVerticle {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    int consumedCount = 0;

    @Override
    public void start() {
        vertx.eventBus().consumer("real_messages", message -> {
            consumedCount++;
            int delay = getRandomConsumeTimeInMillis();
            log.info("consumer has received message '{}' and is going to play for {} millis", message.body(), delay);
            vertx.setTimer(delay, timer -> {
                message.reply("gimme more");
            });
        });
    }
}
