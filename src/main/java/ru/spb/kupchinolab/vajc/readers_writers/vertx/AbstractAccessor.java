package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.ThreadLocalRandom;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.TIME_TO_SLEEP_IN_MILLIS;
import static ru.spb.kupchinolab.vajc.readers_writers.vertx.ActionType.RELEASE_RESOURCE;
import static ru.spb.kupchinolab.vajc.readers_writers.vertx.ActionType.REQUEST_ACCESS;

public abstract class AbstractAccessor extends AbstractVerticle {

    protected final String name;
    private final int maxDelay;

    AbstractAccessor(String name, int maxDelay) {
        this.name = name;
        this.maxDelay = maxDelay;
    }

    @Override
    public void start() {
        System.out.println(name + " started");
        vertx.setTimer(ThreadLocalRandom.current().nextInt(1, maxDelay), new AccessHandler());
    }

    void access(AccessType accessType) {
        JsonObject requestAccess = new JsonObject()
                .put("name", name)
                .put("type", accessType)
                .put("action", REQUEST_ACCESS);
        vertx.eventBus().send("access_queue", requestAccess, event -> {
            if (event.succeeded()) {
                long activeStart = System.currentTimeMillis();
                while (System.currentTimeMillis() <= activeStart + TIME_TO_SLEEP_IN_MILLIS) {
                    //DO NOTHING - emulating active work because Thread.sleep is forbidden inside EventLoop
                }
                int nextDelay = ThreadLocalRandom.current().nextInt(1, maxDelay);
                JsonObject releaseResource = new JsonObject()
                        .put("name", name)
                        .put("type", accessType)
                        .put("action", RELEASE_RESOURCE)
                        .put("nextDelay", nextDelay);
                vertx.eventBus().send("access_queue", releaseResource);
                vertx.setTimer(nextDelay, new AccessHandler());
            } else {
                event.cause().printStackTrace();
            }
        });
    }

    private class AccessHandler implements Handler<Long> {

        @Override
        public void handle(Long event) {
            access();
        }

    }

    abstract protected void access();
}
