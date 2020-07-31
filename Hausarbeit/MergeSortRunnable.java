package pp;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeSortRunnable<E extends Comparable<E>> extends MergeSortSequenziell<E> {

	private final int PROCESSORS = Runtime.getRuntime().availableProcessors() - 1;
	private AtomicInteger counter = new AtomicInteger(0); // count the processors used

	
	/**
	 * a method to sort a list of values in ascending order. the data 
	 * to be sorted must be of the type that implements the Comparable 
	 * interface.
	 * @param 	data	LinkedList to be sorted 
	 * @return 	void	-
	 */
	public void mergeSort(LinkedList<E> data) {

		if (data == null)
			throw new IllegalArgumentException("null is not allowed");

		mergeSortHelper(data, 0, data.size() - 1);
	}

	protected void mergeSortHelper(LinkedList<E> data, int min, int max) {

		// if the resources are exhausted continue with sequential sorting...
		if (counter.get() >= PROCESSORS) {
			super.mergeSortHelper(data, min, max);
			
		} else { // ..otherwise divide the problem
			int len = max - min + 1;
			if (len > 1) {
				int middle = min + (max - min) / 2; // find the middle of the list

				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						counter.incrementAndGet();
						mergeSortHelper(data, min, middle); // sort the left part of the list
						counter.decrementAndGet();
					}
				});
			
				Thread t2 = new Thread(new Runnable() {
					@Override
					public void run() {
						counter.incrementAndGet();
						mergeSortHelper(data, middle + 1, max); // sort the right part of the list
						counter.decrementAndGet();
					}
				});

				t1.start(); // start sorting of the left part
				t2.start();	// start sorting of the right part 

				try {
					t1.join(); // wait until the left part is sorted
					t2.join(); // wait until the right is sorted
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				merge(data, min, middle, middle + 1, max); // merge the both of parts
			} else
				return;
		}
	}
}
