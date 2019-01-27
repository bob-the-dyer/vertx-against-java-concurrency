package ru.spb.kupchinolab.vajc._3_dining_philosophers.old_school;

import java.util.concurrent.locks.ReentrantLock;

class Chopstick extends ReentrantLock {

    final int order;

    Chopstick(int order) {
        this.order = order;
    }
}
