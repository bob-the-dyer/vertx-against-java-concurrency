package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Writer extends Player {

    static AtomicInteger servedWriters = new AtomicInteger(0);

    Writer(String name, Random random, int writerMaxDelay) {
        super(name, random, writerMaxDelay);
    }

    @Override
    public void play() {
        super.play();
        servedWriters.incrementAndGet();
    }

}
