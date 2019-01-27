package ru.spb.kupchinolab.vajc._2_readers_writers.vertx;

import static ru.spb.kupchinolab.vajc._2_readers_writers.Utils.getRandomReadDelayInMillis;
import static ru.spb.kupchinolab.vajc._2_readers_writers.vertx.AccessType.READER;

class Reader extends AbstractAccessor {

    Reader(String name) {
        super(name);
    }

    @Override
    protected void access() {
        access(READER);
    }

    @Override
    protected long getDelay() {
        return getRandomReadDelayInMillis();
    }

}
