package ru.spb.kupchinolab.vajc.readers_writers.vertx;

import static ru.spb.kupchinolab.vajc.readers_writers.vertx.AccessType.WRITER;

class Writer extends AbstractAccessor {

    Writer(String name, int writerMaxDelay) {
        super(name, writerMaxDelay);
    }

    @Override
    protected void access() {
        access(WRITER);
    }

}
