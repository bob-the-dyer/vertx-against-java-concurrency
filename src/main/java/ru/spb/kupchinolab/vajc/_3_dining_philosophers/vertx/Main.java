package ru.spb.kupchinolab.vajc._3_dining_philosophers.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.spb.kupchinolab.vajc._3_dining_philosophers.Utils.PHILOSOPHERS_COUNT;
import static ru.spb.kupchinolab.vajc._3_dining_philosophers.Utils.exitAfterDelay;

public class Main {

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("starting Main");

        Vertx vertx = Vertx.vertx();

        Statistics stats;
        vertx.deployVerticle(stats = new Statistics());

        DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(PHILOSOPHERS_COUNT);
        vertx.deployVerticle(Philosopher.class, deploymentOptions, event -> {
            vertx.eventBus().publish("start_topic", "Go!");
            exitAfterDelay(() -> stats.stats);
        });
    }

}
