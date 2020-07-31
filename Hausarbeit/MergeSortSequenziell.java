package pp;

import java.util.LinkedList;

public class MergeSortSequenziell<E extends Comparable<E>> {

	/**
	 * a method to sort a list of values in ascending order. The data 
	 * to be sorted must be of the type that implements the Comparable 
	 * interface.
	 * @param 	data	LinkedList to be sorted 
	 * @return 	void	
	 */
	public void mergeSort(LinkedList<E> data) {

		if (data == null)
			throw new IllegalArgumentException("The argument cannot be null");

		mergeSortHelper(data, 0, data.size() - 1);
	}

	protected void mergeSortHelper(LinkedList<E> data, int min, int max) {

		int len = max - min + 1;
		if (len > 1) {
			int middle = min + (max - min) / 2;	// find the middle of the list
			mergeSortHelper(data, min, middle);	// sort the left part of the list
			mergeSortHelper(data, middle + 1, max); // sort the right part of the list
			merge(data, min, middle, middle + 1, max); // merge the both of parts
		} else
			return;
	}
	
	
	protected void merge(LinkedList<E> data, int min1, int max1, int min2, int max2) {
		
		LinkedList<E> newList = new LinkedList<>();	// create a temporary list 
		int counter1 = min1, counter2 = min2; // initialize indexes of lists to be merged
		
		// copy items in ascending order to a temporary list
		while (!(counter1 > max1) && !(counter2 > max2)) {
			if (data.get(counter1).compareTo(data.get(counter2)) < 0)
				newList.add(data.get(counter1++));
			else
				newList.add(data.get(counter2++));
		}

		// copy the remaining items of the left or right part of the list
		if (counter1 > max1)
			mergeHelper(data, counter2, max2, newList); // right
		else
			mergeHelper(data, counter1, max1, newList); // left

		// copy the items back to our main list
		while (!(min1 > max2))
			data.set(min1++, newList.removeFirst());
	}

	// copy the remaining items by merging of items 
	private void mergeHelper(LinkedList<E> data, int counter, int max, LinkedList<E> newList) {
		while (!(counter > max))
			newList.add(data.get(counter++));
	}

	/**
	 * output of items to the console
	 */
	public void printList(LinkedList<E> data) {
		data.forEach((temp) -> {
			System.out.println(temp);
		});
	}

	/**
	 * output of items without /n's for easy testing
	 */
	public void printListForTest(LinkedList<E> data) {
		data.forEach((temp) -> {
			System.out.print(temp);
		});
	}
}
