package ru.spb.kupchinolab.vajc._2_readers_writers.vertx;

import io.vertx.core.AbstractVerticle;

public class Statistics extends AbstractVerticle {

    int readersAccessCounter;
    int writerAccessCounter;

    @Override
    public void start() {
        vertx.eventBus().consumer("usage_statistics", event -> {
            AccessType type = AccessType.valueOf((String) event.body());
            if (type == AccessType.WRITER) {
                writerAccessCounter++;
            } else {
                readersAccessCounter++;
            }
        });
    }
}
