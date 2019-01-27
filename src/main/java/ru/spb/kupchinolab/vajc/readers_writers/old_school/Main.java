package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.*;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        //start readers
        IntStream.range(0, NUMBER_OF_READERS).forEach(i ->
                new Reader("reader_" + i, rwLock).start()
        );
        //start writers
        IntStream.range(0, NUMBER_OF_WRITERS).forEach(i ->
                new Writer("writer_" + i, rwLock).start()
        );

        exitAfterDelay(() -> Reader.servedReaders.get(), () -> Writer.servedWriters.get());
    }

}
