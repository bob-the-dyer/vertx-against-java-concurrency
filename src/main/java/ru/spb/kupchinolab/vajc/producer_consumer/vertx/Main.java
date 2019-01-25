package ru.spb.kupchinolab.vajc.producer_consumer.vertx;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc.producer_consumer.Utils.BUFFER_CAPACITY;
import static ru.spb.kupchinolab.vajc.producer_consumer.Utils.exitAfterDelay;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        //TODO implement WriteStream and ReadStream and use Pump, this implementation is incorrect

        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");

        Vertx vertx = Vertx.vertx();
        Consumer consumer = new Consumer();
        Producer producer = new Producer();
        ProducerProxy producerProxy = new ProducerProxy(
                vertx.eventBus().sender("real_messages")
                        .setWriteQueueMaxSize(BUFFER_CAPACITY)
        );
        vertx.deployVerticle(producerProxy);
        vertx.deployVerticle(consumer);
        vertx.deployVerticle(producer);

        exitAfterDelay(() -> consumer.consumedCount, () -> producer.producedCount);
    }

}
