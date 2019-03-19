package ru.spb.kupchinolab.vajc._1_.producer_consumer.vertx;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc._1_.producer_consumer.Utils.getRandomConsumeTimeInMillis;

public class Consumer extends AbstractVerticle {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    int consumedCount = 0;

    @Override
    public void start() {
        vertx.eventBus().consumer("consumption", message -> {
            consumedCount++;
            int delay = getRandomConsumeTimeInMillis();
            log.info("consumer has received message '{}' and is going to play for {} millis", message.body(), delay);
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() < startTime + delay) ;
            message.reply("===" + message.body() + "===");
        });
    }
}
