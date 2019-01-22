package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() + ": starting Main");

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(Statistics.class, new DeploymentOptions());
        vertx.deployVerticle(Mediator.class, new DeploymentOptions());

        //start readers
        IntStream.range(0, NUMBER_OF_READERS).forEach(i ->
                vertx.deployVerticle(new Reader("reader_" + i, READER_MAX_DELAY))
        );
        //start writers
        IntStream.range(0, NUMBER_OF_WRITERS).forEach(i ->
                vertx.deployVerticle(new Writer("writer_" + i, WRITER_MAX_DELAY))
        );

        exitAfterDelay(() -> Statistics.readersAccessCounter, () -> Statistics.writerAccessCounter);

    }

}