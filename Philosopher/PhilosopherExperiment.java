package io.dama.ffi.parcoord.dining.cond;

import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherExperiment {
    static final int MAX_THINKING_DURATION_MS = 3000;
    static final int MAX_EATING_DURATION_MS = 3000;
    static final int MAX_TAKING_TIME_MS = 100;
    static final int PHILOSOPHER_NUM = 5;
    static final int EXP_DURATION_MS = 20000;
    static IPhilosopher[] philosophers = new Philosopher[PHILOSOPHER_NUM];

    public static void main(final String[] args) throws InterruptedException {
        final var table = new ReentrantLock();     
        for (var i = 0; i < PHILOSOPHER_NUM; i++) {
            philosophers[i] = new Philosopher();
            philosophers[i].setTable(table);
            philosophers[i].setSeat(i);
        }
        philosophers[0].setLeft(philosophers[PHILOSOPHER_NUM - 1]);
        philosophers[0].setRight(philosophers[1]);
        for (var i = 1; i < (PHILOSOPHER_NUM - 1); i++) {
            philosophers[i].setLeft(philosophers[i - 1]);
            philosophers[i].setRight(philosophers[i + 1]);
        }
        philosophers[PHILOSOPHER_NUM - 1].setLeft(philosophers[PHILOSOPHER_NUM - 2]);
        philosophers[PHILOSOPHER_NUM - 1].setRight(philosophers[0]);
        for (var i = 0; i < PHILOSOPHER_NUM; i++) {
            philosophers[i].start();
        }
        Thread.sleep(EXP_DURATION_MS);
        for (var i = 0; i < PHILOSOPHER_NUM; i++) {
            philosophers[i].stopPhilosopher();
        }
    }
}
