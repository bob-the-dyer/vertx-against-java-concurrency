package ru.spb.kupchinolab.vajc._2_.readers_writers.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc._2_.readers_writers.Utils.*;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        Vertx vertx = Vertx.vertx();

        Statistics stats;
        vertx.deployVerticle(stats = new Statistics());
        vertx.deployVerticle(new Mediator());
        vertx.deployVerticle(Reader.class, new DeploymentOptions().setInstances(NUMBER_OF_READERS), compRead -> {
            vertx.deployVerticle(Writer.class, new DeploymentOptions().setInstances(NUMBER_OF_WRITERS), compWrite -> {
                log.info("all verticals are ready");
                vertx.eventBus().publish("start_topic", "Go-go-go!");
                exitAfterDelay(() -> stats.readersAccessCounter, () -> stats.writerAccessCounter);
            });
        });
    }
}
