package Philosopher;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherExperiment {

	static final int MAX_THINKING_DURATION_MS = 3000;
	static final int MAX_EATING_DURATION_MS = 3000;
	static final int MAX_TAKING_TIME_MS = 100;
	static final int PHILOSOPHER_NUM = 5;
	static final int EXP_DURATION_MS = 20000;
	static Chopstick[] chopsticks = new Chopstick[PHILOSOPHER_NUM];
	static Philosopher[] philosophers = new Philosopher[PHILOSOPHER_NUM];
	static Lock table = new ReentrantLock();

	public static void main(final String[] args) throws InterruptedException {

		for (int i = 0; i < PHILOSOPHER_NUM; i++) {
			chopsticks[i] = new Chopstick();
		}
		
		for (int i = 0; i < PHILOSOPHER_NUM; i++) {
			philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % PHILOSOPHER_NUM],table);
			philosophers[i].setTable(table);
		}
		philosophers[PHILOSOPHER_NUM - 1] = new Philosopher(chopsticks[0], chopsticks[PHILOSOPHER_NUM - 1],table);

		for (int i = 0; i < PHILOSOPHER_NUM; i++) {
			if(i == 0) {
				philosophers[i].setRightNeighbour(philosophers[(i + 1) % PHILOSOPHER_NUM]);
				philosophers[i].setLeftNeighbour(philosophers[PHILOSOPHER_NUM-1]);
			} else if(i == PHILOSOPHER_NUM) {
				philosophers[i].setRightNeighbour(philosophers[0]);
				philosophers[i].setLeftNeighbour(philosophers[i-1]);
			} else {
				philosophers[i].setRightNeighbour(philosophers[(i + 1) % PHILOSOPHER_NUM]);
				philosophers[i].setLeftNeighbour(philosophers[i-1]);
			}
		}

		for (int i = 0; i < PHILOSOPHER_NUM; i++) {
			philosophers[i].start();
		}

		Thread.sleep(EXP_DURATION_MS);

		for (int i = 0; i < PHILOSOPHER_NUM; i++) {
			philosophers[i].stopPhilosopher();
		}
	}

}
