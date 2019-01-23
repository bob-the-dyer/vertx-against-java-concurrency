package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.*;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");

        Vertx vertx = Vertx.vertx();

        Statistics stats;
        vertx.deployVerticle(stats = new Statistics());
        vertx.deployVerticle(new Mediator());

        //start readers
        IntStream.range(0, NUMBER_OF_READERS).forEach(i ->
                vertx.deployVerticle(new Reader("reader_" + i, READER_MAX_DELAY))
        );
        //start writers
        IntStream.range(0, NUMBER_OF_WRITERS).forEach(i ->
                vertx.deployVerticle(new Writer("writer_" + i, WRITER_MAX_DELAY))
        );

        exitAfterDelay(() -> stats.readersAccessCounter, () -> stats.writerAccessCounter);

    }

}
