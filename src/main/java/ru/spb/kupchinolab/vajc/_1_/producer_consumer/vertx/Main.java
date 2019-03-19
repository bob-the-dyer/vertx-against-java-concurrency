package ru.spb.kupchinolab.vajc._1_.producer_consumer.vertx;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc._1_.producer_consumer.Utils.exitAfterDelay;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        Vertx vertx = Vertx.vertx();

        Consumer consumer = new Consumer();
        Buffer buffer = new Buffer();
        Producer producer = new Producer();

        vertx.deployVerticle(consumer);
        vertx.deployVerticle(buffer);
        vertx.deployVerticle(producer);

        exitAfterDelay(() -> consumer.consumedCount, () -> producer.producedCount);
    }

}
