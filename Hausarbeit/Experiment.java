package pp;

import java.util.LinkedList;
import java.util.Random;

public class Experiment<E extends Comparable<E>> {

	private final LinkedList<E> list;

	public Experiment(LinkedList<E> list) {
		this.list = list;
	}

	@SuppressWarnings("unchecked")
	public void carryOut() {

		// Sequentially
		System.out.println("Sequentially");
		LinkedList<E> try1 = (LinkedList<E>) this.list.clone();

		MergeSortSequenziell<E> ms1 = new MergeSortSequenziell<>();
		long timeStart = System.currentTimeMillis();
		ms1.mergeSort(try1);
		long timeEnd = System.currentTimeMillis();
		System.out.println("Execution time: " + (timeEnd - timeStart) + " milliseconds.");

		// Runnable
		System.out.println("Runnable");
		LinkedList<E> try2 = (LinkedList<E>) this.list.clone();

		MergeSortRunnable<E> ms2 = new MergeSortRunnable<>();
		timeStart = System.currentTimeMillis();
		ms2.mergeSort(try2);
		timeEnd = System.currentTimeMillis();
		System.out.println("Execution time: " + (timeEnd - timeStart) + " milliseconds.");

		// ThreadPool
		System.out.println("Threadpool");
		LinkedList<E> try3 = (LinkedList<E>) this.list.clone();

		MergeSortThreadPool<E> ms3 = new MergeSortThreadPool<>();
		timeStart = System.currentTimeMillis();
		ms3.mergeSort(try3);
		timeEnd = System.currentTimeMillis();
		System.out.println("Execution time: " + (timeEnd - timeStart) + " milliseconds.");

		// ForkJoin
		System.out.println("ForkJoin");
		LinkedList<E> try4 = (LinkedList<E>) this.list.clone();

		MergeSortForkJoin<E> ms4 = new MergeSortForkJoin<>();
		timeStart = System.currentTimeMillis();
		ms4.mergeSort(try4);
		timeEnd = System.currentTimeMillis();
		System.out.println("Execution time: " + (timeEnd - timeStart) + " milliseconds.");

	}

	public static void main(String[] args) {

		Random r = new Random();
		LinkedList<Integer> list = new LinkedList<Integer>();

		// Measure points 1-4
		System.out.println("An Experiment with 10 items:");
		for (int i = 0; i < 10; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();
		
		// Measure points 5-8
		System.out.println("\nAAn Experiment with 100 items:");
		for (int i = 0; i < 100; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

		// Measure points 9-12
		System.out.println("\nAAn Experiment with 1000 items:");
		for (int i = 0; i < 1000; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

		// Measure points 13-16
		list = new LinkedList<Integer>();
		System.out.println("\nAn Experiment with 5000 items:");
		for (int i = 0; i < 5000; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

		// Measure points 17-20
		list = new LinkedList<Integer>();
		System.out.println("\nAn Experiment with 10000 items:");
		for (int i = 0; i < 10000; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

		// Measure points 21-24
		list = new LinkedList<Integer>();
		System.out.println("\nAn Experiment with 50000 items:");
		for (int i = 0; i < 50000; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

	}

}
