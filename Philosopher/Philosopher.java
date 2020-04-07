package Philosopher;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Philosopher extends Thread {

	private final Chopstick left;
	private final Chopstick right;
	private Philosopher leftNeighbour;
	private Philosopher rightNeighbour;
	private final Random random;
	private int eaten;
	private volatile boolean stop;
	private Lock table;
	private final Condition isBusy;
	private boolean isEating = false;

	public Philosopher(Chopstick left, Chopstick right, Lock table) {
		this.left = left;
		this.right = right;
		this.stop = false;
		this.random = new Random();
		this.eaten = 0;
		this.table = table;
		this.isBusy = table.newCondition();
	}
	
	public void setLeftNeighbour(Philosopher left) {
		leftNeighbour = left;
	}
	public void setRightNeighbour(Philosopher right) {
		rightNeighbour = right;
	}
	public void setTable(Lock table) {
		this.table = table;
	}
	
	public void stopPhilosopher() {
		System.out.println(getId() + " stopping");
		stop = true;
		interrupt();	
	}
	
	 @Override
	 public void run() {
		 System.out.println(getId() + " running");
		 try {
			 while (!this.stop) {
				 think();
	             eat();
	         }
		 } catch (InterruptedException e) {} 
		 System.out.println(Thread.currentThread().getId() + " stopped; eaten=" + this.eaten);
	 }
	 
	 private void think() throws InterruptedException {
		 Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_THINKING_DURATION_MS));
	 }
	 
	 private void eat() throws InterruptedException {
		 System.out.println(Thread.currentThread().getId() + " try taking sticks");	
		 table.lock();	  
		 try {
			 while(leftNeighbour.isEating && rightNeighbour.isEating) {
				 isBusy.await();
			 }
			 isEating = true;
			 System.out.println(Thread.currentThread().getId() + " sticks acquired ");
			 Thread.sleep(this.random.nextInt(PhilosopherExperiment.MAX_TAKING_TIME_MS));
			 System.out.println(Thread.currentThread().getId() + " eating");
             this.eaten++;
             Thread.sleep(PhilosopherExperiment.MAX_EATING_DURATION_MS);
		 } finally {
			 table.unlock();
			 System.out.println(Thread.currentThread().getId() + " sticks released");
			 isEating = false;
		 }	 
	 }
}
