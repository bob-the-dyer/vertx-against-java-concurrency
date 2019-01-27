package ru.spb.kupchinolab.vajc._1_.producer_consumer.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.spb.kupchinolab.vajc._1_.producer_consumer.Utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.spb.kupchinolab.vajc._1_.producer_consumer.Utils.*;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        LinkedBlockingDeque<Integer> queue = new LinkedBlockingDeque<>(Utils.BUFFER_CAPACITY);

        AtomicInteger consumedCount = new AtomicInteger(0);
        AtomicInteger producedCount = new AtomicInteger(0);

        new Thread(() -> {
            while (true) {
                try {
                    log.info("consumer is about to consume from queue with size {}", queue.size());

                    Integer message = queue.take();
                    consumedCount.incrementAndGet();

                    int randomConsumeTimeInMillis = getRandomConsumeTimeInMillis();
                    log.info("consumer is going to play with '{}' for {} millis", message, randomConsumeTimeInMillis);
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

                    int counter = producedCount.incrementAndGet();
                    log.info("producer is about to put '{}' to queue with size {}", counter, queue.size());
                    queue.put(counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        exitAfterDelay(() -> consumedCount.get(), () -> producedCount.get());
    }
}
