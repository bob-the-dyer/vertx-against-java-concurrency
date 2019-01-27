package ru.spb.kupchinolab.vajc._1_.producer_consumer.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import static ru.spb.kupchinolab.vajc._1_.producer_consumer.Utils.BUFFER_CAPACITY;

public class Buffer extends AbstractVerticle {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private Queue<Message<?>> buffer = new LinkedList<>();
    private Consumer onConsumerReplyAction;

    @Override
    public void start() {
        vertx.eventBus().consumer("production", event -> {
            log.info("buffer has received '{}'", event.body());
            buffer.offer(event);
            log.info("buffer size is '{}'", buffer.size());
            if (buffer.size() < BUFFER_CAPACITY) {
                event.reply("gimme more");
                onConsumerReplyAction = o -> {
                };
            } else {
                log.info("buffer size '{}' is overlimited", buffer.size());
                onConsumerReplyAction = o -> {
                    event.reply("gimme more");
                };
            }
            if (buffer.size() == 1) {
                Message<?> head = buffer.peek();
                vertx.eventBus().send("consumption", head.body(), new ConsumerReplyHandler());
            }
        });
    }

    class ConsumerReplyHandler implements Handler<AsyncResult<Message<Object>>> {

        @Override
        public void handle(AsyncResult<Message<Object>> event) {
            log.info("handling reply for '{}', current buffer size is {}", event.result().body(), buffer.size());
            buffer.remove();
            Message<?> head = buffer.peek();
            if (head != null) {
                vertx.eventBus().send("consumption", head.body(), new ConsumerReplyHandler());
            }
            onConsumerReplyAction.accept(event);
        }
    }
}
