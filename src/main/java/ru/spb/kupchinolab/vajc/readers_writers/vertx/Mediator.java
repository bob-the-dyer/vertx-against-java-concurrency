package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import ru.spb.kupchinolab.vajc.readers_writers.Utils;

import java.util.LinkedList;
import java.util.List;

public class Mediator extends AbstractVerticle {

    //TODO optimize for several Mediator verticals
    private int writerAccessedCount = 0;
    private int readerAccessedCount = 0;
    private final List<Message<JsonObject>> awaitingAccessList = new LinkedList<>();

    @Override
    public void start() {
        vertx.eventBus().consumer("access_queue", (Handler<Message<JsonObject>>) event -> {
            JsonObject message = event.body();
            String name = message.getString("name");
            AccessType type = AccessType.valueOf(message.getString("type"));
            ActionType action = ActionType.valueOf(message.getString("action"));

            if (type == AccessType.WRITER && action == ActionType.RELEASE_RESOURCE) {
                assert readerAccessedCount == 0;
                assert writerAccessedCount > 0;

                logAndStat(name, type, message.getInteger("nextDelay"));

                for (; ; ) {
                    if (awaitingAccessList.size() > 0) {
                        Message<JsonObject> m = awaitingAccessList.get(0);
                        JsonObject json = m.body();
                        awaitingAccessList.remove(0);
                        m.reply("OK");
                        if (AccessType.WRITER == AccessType.valueOf(json.getString("type"))) {
                            break;
                        } else {
                            readerAccessedCount++;
                        }
                    } else {
                        writerAccessedCount--;
                        break;
                    }
                }
            } else if (type == AccessType.READER && action == ActionType.RELEASE_RESOURCE) {
                assert readerAccessedCount > 0;
                assert writerAccessedCount == 0;

                logAndStat(name, type, message.getInteger("nextDelay"));

                readerAccessedCount--;

                if (readerAccessedCount == 0 && awaitingAccessList.size() > 0) {
                    Message<JsonObject> m = awaitingAccessList.get(0);
                    JsonObject json = m.body();

                    assert AccessType.WRITER == AccessType.valueOf(json.getString("type"));

                    writerAccessedCount++;
                    awaitingAccessList.remove(0);
                    m.reply("OK");
                }
            } else if (type == AccessType.READER && action == ActionType.REQUEST_ACCESS) {
                if (writerAccessedCount > 0) {
                    awaitingAccessList.add(event);
                } else {
                    readerAccessedCount++;
                    event.reply("OK");
                }
            } else if (type == AccessType.WRITER && action == ActionType.REQUEST_ACCESS) {
                if (readerAccessedCount == 0 && writerAccessedCount == 0) {
                    writerAccessedCount++;
                    event.reply("OK");
                } else {
                    awaitingAccessList.add(event);
                }
            }
        });
    }

    private void logAndStat(String name, AccessType type, Integer nextDelay) {
        Utils.log(name, readerAccessedCount, writerAccessedCount, awaitingAccessList.size(), nextDelay);
        vertx.eventBus().send("usage_statistics", type.name());
    }
}
