package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import static ru.spb.kupchinolab.vajc.readers_writers.vertx.AccessType.READER;

class Reader extends AbstractAccessor {

    Reader(String name, int readerMaxDelay) {
        super(name, readerMaxDelay);
    }

    @Override
    protected void access() {
        access(READER);
    }

}
