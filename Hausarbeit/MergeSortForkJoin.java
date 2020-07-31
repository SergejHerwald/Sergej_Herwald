package pp;

import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSortForkJoin<E extends Comparable<E>> extends MergeSortSequenziell<E> {

	private ForkJoinPool pool = new ForkJoinPool();
	
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

		pool.invoke(new MyTask(data, 0, data.size() - 1));
	}

	public class MyTask extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		private LinkedList<E> data;
		private int min;
		private int max;

		public MyTask(LinkedList<E> data, int min, int max) {
			this.data = data;
			this.min = min;
			this.max = max;
		}

		@Override
		protected void compute() {
			
			int len = max - min + 1;
			if (len > 1) {
				int middle = min + (max - min) / 2; // find the middle of the list
				// sort the right and the left parts of the list and wait until the both of them are sorted
				invokeAll((new MyTask(data, min, middle)), (new MyTask(data, middle + 1, max)));
				merge(data, min, middle, middle + 1, max); // merge the both of parts
			}
		}
	}
}
