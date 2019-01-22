package ru.spb.kupchinolab.vajc.readers_writers.old_school;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() + ": starting Main");

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        //start readers
        IntStream.range(0, NUMBER_OF_READERS).forEach(i ->
                new Reader("reader_" + i, READER_MAX_DELAY, rwLock).start()
        );
        //start writers
        IntStream.range(0, NUMBER_OF_WRITERS).forEach(i ->
                new Writer("writer_" + i, WRITER_MAX_DELAY, rwLock).start()
        );

        exitAfterDelay(() -> Reader.servedReaders.get(), () -> Writer.servedWriters.get());
    }

}
