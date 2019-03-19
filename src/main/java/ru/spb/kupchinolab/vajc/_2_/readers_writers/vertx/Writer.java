package ru.spb.kupchinolab.vajc._2_.readers_writers.vertx;

import static ru.spb.kupchinolab.vajc._2_.readers_writers.Utils.getRandomWriteDelayInMillis;
import static ru.spb.kupchinolab.vajc._2_.readers_writers.vertx.AccessType.WRITER;

public class Writer extends AbstractAccessor {

    private static int globalOrder = 0;

    public Writer() {
        super("writer_" + globalOrder++);
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
