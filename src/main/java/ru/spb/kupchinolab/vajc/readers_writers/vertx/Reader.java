package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Reader extends Player {

    static AtomicInteger servedReaders = new AtomicInteger(0);

    Reader(String name, Random random, int readerMaxDelay) {
        super(name, random, readerMaxDelay);
    }

    @Override
    public void play() {
        super.play();
        servedReaders.incrementAndGet();
    }

}
