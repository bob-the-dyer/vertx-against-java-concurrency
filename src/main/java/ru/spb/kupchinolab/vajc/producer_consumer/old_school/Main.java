package ru.spb.kupchinolab.vajc.producer_consumer.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.spb.kupchinolab.vajc.producer_consumer.Utils.*;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());
    static AtomicInteger consumedCount = new AtomicInteger(0);
    static AtomicInteger producedCount = new AtomicInteger(0);

    public static void main(String[] args) {
        log.info("starting Main");

        LinkedBlockingDeque<Object> queue = new LinkedBlockingDeque<>(BUFFER_CAPACITY);

        new Thread(() -> {
            while (true) {
                try {
                    log.info("consumer is about to consume from queue with size {}", queue.size());

                    queue.take();
                    consumedCount.incrementAndGet();

                    int randomConsumeTimeInMillis = getRandomConsumeTimeInMillis();
                    log.info("consumer is going to play for {} millis", randomConsumeTimeInMillis);
                    Thread.sleep(randomConsumeTimeInMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    int randomProduceTimeInMillis = getRandomProduceTimeInMillis();
                    log.info("producer is going to work for {} millis", randomProduceTimeInMillis);
                    Thread.sleep(randomProduceTimeInMillis);

                    log.info("producer is about to put to queue with size {}", queue.size());

                    queue.put(new Object());
                    producedCount.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        exitAfterDelay(() -> consumedCount.get(), () -> producedCount.get());
    }
}
