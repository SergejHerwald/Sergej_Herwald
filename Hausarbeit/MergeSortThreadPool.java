package pp;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeSortThreadPool<E extends Comparable<E>> extends MergeSortSequenziell<E> {

	private final int PROCESSORS = Runtime.getRuntime().availableProcessors()-1;
	private ExecutorService es = Executors.newCachedThreadPool();
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
		es.shutdown();	
	}

	protected void mergeSortHelper(LinkedList<E> data, int min, int max) {

		// if the resources are exhausted continue with sequential sorting...
		if (counter.get() >= PROCESSORS) {
			super.mergeSortHelper(data, min, max);
		} else { // ..otherwise divide the problem
			int len = max - min + 1;
			if (len > 1) {
				int middle = min + (max - min) / 2;
					
				Callable<Void> c1 = () -> {
					counter.incrementAndGet();
					mergeSortHelper(data, min, middle); // sort the left part of the list
					counter.decrementAndGet();
					return null;
				};
				
				Callable<Void> c2 = () -> {
					counter.incrementAndGet();
					mergeSortHelper(data, middle + 1, max); // sort the right part of the list
					counter.decrementAndGet();
					return null;
				};

				// sort the right and the left parts of the list 
				// and wait until the both of part are sorted
				try {
					es.invokeAll(Arrays.asList(c1,c2)); 
				} catch (InterruptedException e) {				
					e.printStackTrace();
				}
				merge(data, min, middle, middle + 1, max); // merge the both of parts
			} else
				return;
		}
	}
}
