package pp2;

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
		System.out.println("Sequentially: ");
		MergeSortSequenziell<E> ms = new MergeSortSequenziell<E>();
		LinkedList<E> try1 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try2 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try3 = (LinkedList<E>) this.list.clone();

		long timeStart = System.currentTimeMillis();
		ms.mergeSort(try1);
		long timeEnd = System.currentTimeMillis();
		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

//		timeStart = System.currentTimeMillis();
//		ms.mergeSort(try2);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");
//
//		timeStart = System.currentTimeMillis();
//		ms.mergeSort(try3);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

		
		// Runnable
		System.out.println("Runnable: ");
		MergeSortRunnable<E> ms1 = new MergeSortRunnable<>();
		LinkedList<E> try4 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try5 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try6 = (LinkedList<E>) this.list.clone();

		timeStart = System.currentTimeMillis();
		ms1.mergeSort(try4);
		timeEnd = System.currentTimeMillis();
		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

//		timeStart = System.currentTimeMillis();
//		ms1.mergeSort(try5);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");
//
//		timeStart = System.currentTimeMillis();
//		ms1.mergeSort(try6);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

		
		// ThreadPool
		System.out.println("Threadpool: ");
		MergeSortThreadPool<E> ms2 = new MergeSortThreadPool<>();
		LinkedList<E> try7 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try8 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try9 = (LinkedList<E>) this.list.clone();

		timeStart = System.currentTimeMillis();
		ms2.mergeSort(try7);
		timeEnd = System.currentTimeMillis();
		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

//		timeStart = System.currentTimeMillis();
//		ms2.mergeSort(try8);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");
//
//		timeStart = System.currentTimeMillis();
//		ms2.mergeSort(try9);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

		
		// ForkJoin
		System.out.println("ForkJoin: ");
		MergeSortForkJoin<E> ms3 = new MergeSortForkJoin<>();
		LinkedList<E> try10 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try11 = (LinkedList<E>) this.list.clone();
//		LinkedList<E> try12 = (LinkedList<E>) this.list.clone();

		timeStart = System.currentTimeMillis();
		ms3.mergeSort(try10);
		timeEnd = System.currentTimeMillis();
		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

//		timeStart = System.currentTimeMillis();
//		ms3.mergeSort(try11);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");
//
//		timeStart = System.currentTimeMillis();
//		ms3.mergeSort(try12);
//		timeEnd = System.currentTimeMillis();
//		System.out.println("Verlaufszeit: " + (timeEnd - timeStart) + " Millisek.");

	}

	public static void main(String[] args) {

		Random r = new Random();
		LinkedList<Integer> list = new LinkedList<Integer>();

		// Measure points 1-4
		System.out.println("An Experiment with 100 elements:");
		for (int i = 0; i < 100; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

		// Measure points 5-8
		list = new LinkedList<Integer>();
		System.out.println("\nAn Experiment with 1000 elements:");
		for (int i = 0; i < 1000; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

		// Measure points 9-12
		list = new LinkedList<Integer>();
		System.out.println("\nAn Experiment with 10000 elements:");
		for (int i = 0; i < 10000; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

		// Measure points 13-16
		list = new LinkedList<Integer>();
		System.out.println("\nAn Experiment with 50000 elements:");
		for (int i = 0; i < 50000; i++)
			list.add(1 + r.nextInt(50000));
		new Experiment<Integer>(list).carryOut();

	}

}
