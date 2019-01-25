package ru.spb.kupchinolab.vajc.producer_consumer.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.streams.WriteStream;

public class ProducerProxy extends AbstractVerticle {

    private WriteStream<Object> consumerProxy;

    public ProducerProxy(WriteStream<Object> consumerProxy) {
        this.consumerProxy = consumerProxy;
    }

    @Override
    public void start() {
        MessageConsumer<Object> internalConsumer = vertx.eventBus().consumer("messages");
        internalConsumer.handler(event -> {
            Object body = event.body();
            consumerProxy.write(body);
            if (consumerProxy.writeQueueFull()) {
                internalConsumer.pause();
                consumerProxy.drainHandler(done -> {
                    internalConsumer.resume();
                    event.reply("go ahead!");
                });
            } else {
                event.reply("go ahead!");
            }
        });
    }
}
