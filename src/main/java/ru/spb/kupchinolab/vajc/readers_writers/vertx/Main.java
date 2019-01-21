package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import io.vertx.core.Vertx;

import java.util.Random;
import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc.readers_writers.Constants.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() + ": starting Main");
        Random random = new Random();

        Vertx vertx = Vertx.vertx();
        //start readers
        IntStream.range(0, NUMBER_OF_READERS).forEach(i ->
                vertx.deployVerticle(new Reader("reader_" + i, random, READER_MAX_DELAY))
        );
        //start writers
        IntStream.range(0, NUMBER_OF_WRITERS).forEach(i ->
                vertx.deployVerticle(new Writer("writer_" + i, random, WRITER_MAX_DELAY))
        );

        exitAfterDelay(() -> Reader.servedReaders.get(), () -> Writer.servedWriters.get());
    }

}
