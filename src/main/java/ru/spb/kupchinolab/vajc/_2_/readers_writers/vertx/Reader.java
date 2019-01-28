package ru.spb.kupchinolab.vajc._2_.readers_writers.vertx;

import static ru.spb.kupchinolab.vajc._2_.readers_writers.Utils.getRandomReadDelayInMillis;
import static ru.spb.kupchinolab.vajc._2_.readers_writers.vertx.AccessType.READER;

public class Reader extends AbstractAccessor {

    static int globalOrder = 0;

    public Reader() {
        super("reader_" + globalOrder++);
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
