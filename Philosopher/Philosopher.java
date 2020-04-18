package io.dama.ffi.parcoord.dining.cond;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread implements IPhilosopher {

	private Philosopher left;
	private Philosopher right;
	private boolean stop;
	private volatile boolean eating = false;
	private int seat;
	private Lock table;

	private final Random random = new Random();
	private int eaten;
	private Condition noSticks;

	@Override
	public void setLeft(final IPhilosopher left) {
		this.left = (Philosopher) left;
	}

	@Override
	public void setRight(final IPhilosopher right) {
		this.right = (Philosopher) right;
	}

	@Override
	public void setSeat(final int seat) {
		this.seat = seat;
	}

	@Override
	public void setTable(final Lock table) {
		this.table = table;
		this.noSticks = table.newCondition();;
	}

	// termination of Thread's work
	@Override
	public void stopPhilosopher() {
		System.out.println("Philosopher-" + this.seat + ": stopping");
		this.stop = true;
		interrupt();
	}

	@Override
	public void run() {
		System.out.println("Philosopher-" + this.seat + ": running");
		try {
			while (!this.stop) {
				eat();
				think();
			}
		} catch (InterruptedException e) {
		}
		System.out.println("Philosopher-" + this.seat + ": stopped; eaten=" + this.eaten);
	}

	private void think() throws InterruptedException {
		Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_THINKING_DURATION_MS));
		System.out.println("Philosopher-" + this.seat + ": thinking");
	}

	private void eat() throws InterruptedException {
		System.out.println("Philosopher-" + this.seat + ": try taking sticks");
		table.lock();
		try {
			// checks if his neighbors are eating
			while (left.eating || right.eating) {
				noSticks.await();
			}
			System.out.println("Philosopher-" + this.seat + ": sticks acquired ");				
		} finally {
			table.unlock();
		}

		// is eating
		eating = true;
		Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_TAKING_TIME_MS));
		System.out.println("Philosopher-" + this.seat + ": eating");	
		Thread.sleep(PhilosopherExperiment.MAX_EATING_DURATION_MS);
		this.eaten++;
		
		// the meal is over
		System.out.println("Philosopher-" + this.seat + ": sticks released");
		eating = false;
		table.lock();
		try {
			left.noSticks.signal();
			right.noSticks.signal();
		} finally {
			table.unlock();
		}
	}
}
