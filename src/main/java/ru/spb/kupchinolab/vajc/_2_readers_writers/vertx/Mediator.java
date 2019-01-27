package ru.spb.kupchinolab.vajc._2_readers_writers.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import ru.spb.kupchinolab.vajc._2_readers_writers.Utils;

import java.util.LinkedList;
import java.util.Queue;

public class Mediator extends AbstractVerticle {

    private int writerAccessedCount = 0;
    private int readerAccessedCount = 0;
    private Queue<Message<JsonObject>> awaitingAccessList = new LinkedList<>();

    @Override
    public void start() {
        vertx.eventBus().consumer("access_queue", (Handler<Message<JsonObject>>) event -> {
            JsonObject message = event.body();
            String name = message.getString("name");
            AccessType type = AccessType.valueOf(message.getString("type"));
            ActionType action = ActionType.valueOf(message.getString("action"));

            assert readerAccessedCount >= 0;
            assert writerAccessedCount == 0 || writerAccessedCount == 1;

            if (type == AccessType.READER && action == ActionType.REQUEST_ACCESS) {
                if (writerAccessedCount > 0) {
                    awaitingAccessList.offer(event);
                } else {
                    readerAccessedCount++;
                    event.reply("OK");
                }
            } else if (type == AccessType.WRITER && action == ActionType.REQUEST_ACCESS) {
                if (readerAccessedCount == 0 && writerAccessedCount == 0) {
                    writerAccessedCount++;
                    event.reply("OK");
                } else {
                    awaitingAccessList.offer(event);
                }
            } else if (type == AccessType.READER && action == ActionType.RELEASE_RESOURCE) {
                assert readerAccessedCount > 0;
                assert writerAccessedCount == 0;

                logAndStat(name, type, message.getLong("nextDelay"));
                readerAccessedCount--;
                event.reply("OK");

                if (readerAccessedCount == 0 && awaitingAccessList.size() > 0) {
                    Message<JsonObject> m = awaitingAccessList.poll();
                    JsonObject json = m.body();

                    assert AccessType.WRITER == AccessType.valueOf(json.getString("type"));

                    writerAccessedCount++;
                    m.reply("OK");
                }
            } else if (type == AccessType.WRITER && action == ActionType.RELEASE_RESOURCE) {
                assert readerAccessedCount == 0;
                assert writerAccessedCount == 1;

                logAndStat(name, type, message.getLong("nextDelay"));
                writerAccessedCount--;
                event.reply("OK");

                for (; ; ) {
                    if (awaitingAccessList.size() > 0) {
                        Message<JsonObject> m = awaitingAccessList.peek();
                        JsonObject json = m.body();
                        if (AccessType.WRITER == AccessType.valueOf(json.getString("type"))) {
                            if (readerAccessedCount == 0) {
                                awaitingAccessList.remove();
                                writerAccessedCount++;
                                m.reply("OK");
                            }
                            break;
                        } else {
                            awaitingAccessList.remove();
                            readerAccessedCount++;
                            m.reply("OK");
                        }
                    } else {
                        break;
                    }
                }
            }
        });
    }

    private void logAndStat(String name, AccessType type, long nextDelay) {
        Utils.log(name, readerAccessedCount, writerAccessedCount, awaitingAccessList.size(), nextDelay);
        vertx.eventBus().send("usage_statistics", type.name());
    }
}
