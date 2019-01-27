package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import static ru.spb.kupchinolab.vajc.readers_writers.Utils.getRandomWriteDelayInMillis;
import static ru.spb.kupchinolab.vajc.readers_writers.vertx.AccessType.WRITER;

class Writer extends AbstractAccessor {

    Writer(String name) {
        super(name);
    }

    @Override
    protected void access() {
        access(WRITER);
    }

    @Override
    protected long getDelay() {
        return getRandomWriteDelayInMillis();
    }


}
