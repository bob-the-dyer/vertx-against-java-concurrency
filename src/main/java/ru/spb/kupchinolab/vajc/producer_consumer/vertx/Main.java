package ru.spb.kupchinolab.vajc.producer_consumer.vertx;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc.producer_consumer.Utils.exitAfterDelay;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");

        Vertx vertx = Vertx.vertx();
        Buffer buffer = new Buffer();
        Consumer consumer = new Consumer();
        Producer producer = new Producer();

        vertx.deployVerticle(buffer);
        vertx.deployVerticle(consumer);
        vertx.deployVerticle(producer);

        exitAfterDelay(() -> consumer.consumedCount, () -> producer.producedCount);
    }

}
