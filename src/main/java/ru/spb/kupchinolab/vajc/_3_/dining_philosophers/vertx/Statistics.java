package ru.spb.kupchinolab.vajc._3_.dining_philosophers.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Statistics extends AbstractVerticle {

    Map<Integer, Map<String, Integer>> stats = new HashMap<>();

    @Override
    public void start() {
        vertx.eventBus().consumer("usage_statistics", (Handler<Message<JsonObject>>) event -> {
            JsonObject usage = event.body();

            Integer order = usage.getInteger("order");
            Integer eatTime = usage.getInteger("eatTime");
            Integer count = usage.getInteger("count");

            Map<String, Integer> newStat = new HashMap<>();
            newStat.put("totalTime", eatTime);
            newStat.put("count", count);

            stats.merge(order, newStat, (oldStat, delta) -> {
                Map<String, Integer> mergedStat = new HashMap<>();
                mergedStat.put("totalTime", oldStat.get("totalTime") + delta.get("totalTime"));
                mergedStat.put("count", oldStat.get("count") + delta.get("count"));
                return mergedStat;
            });
        });
    }
}
