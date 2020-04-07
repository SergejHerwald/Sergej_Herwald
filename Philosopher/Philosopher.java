package io.dama.ffi.parcoord.dining.cond;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

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
	}

	public void setCondition(final Condition condition) {
		this.noSticks = condition;
	}

	@Override
	public void stopPhilosopher() {
		System.out.println(getName() + " stopping");
		this.stop = true;
		interrupt();
	}

	// --------------------------------------------------------
	@Override
	public void run() {
		System.out.println(getName() + " running");
		try {
			while (!this.stop) {
				think();
				eat();
			}
		} catch (InterruptedException e) {
		}
		System.out.println(Thread.currentThread().getName() + " stopped; eaten=" + this.eaten);
	}

	private void think() throws InterruptedException {
		Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_THINKING_DURATION_MS));
	}

	private void eat() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " try taking sticks");
		table.lock();
		try {
			while (left.eating && right.eating) {
				noSticks.await();
			}
			eating = true;
			System.out.println(Thread.currentThread().getName() + " sticks acquired ");
			Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_TAKING_TIME_MS));
			System.out.println(Thread.currentThread().getName() + " eating");
			this.eaten++;
			Thread.sleep(PhilosopherExperiment.MAX_EATING_DURATION_MS);
			eating = false;
			noSticks.signalAll();
			System.out.println(Thread.currentThread().getName() + " sticks released");
		} finally {		
			table.unlock();
		}
	}
}
