package ru.spb.kupchinolab.vajc._2_.readers_writers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc._2_.readers_writers.Utils.*;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true); //try unfair lock as well

        CountDownLatch latch = new CountDownLatch(1);

        IntStream.range(0, NUMBER_OF_READERS).forEach(i ->
                new Reader("reader_" + i, rwLock, latch).start()
        );
        IntStream.range(0, NUMBER_OF_WRITERS).forEach(i ->
                new Writer("writer_" + i, rwLock, latch).start()
        );
        log.info("all threads are ready");

        latch.countDown();

        exitAfterDelay(() -> Reader.servedReaders.get(), () -> Writer.servedWriters.get());
    }

}
