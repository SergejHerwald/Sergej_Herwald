package pp2;

import java.util.LinkedList;
import java.util.Random;

public class MergeSortSequenziell<E extends Comparable<E>> {

	private final int THRESHOLD = 1;

	public void mergeSort(LinkedList<E> data) {

		if (data == null)
			throw new IllegalArgumentException("null is not allowed");

		mergeSortHelper(data, 0, data.size() - 1);
	}

	private void mergeSortHelper(LinkedList<E> data, int min, int max) {

		int len = max - min + 1;
		if (len > THRESHOLD) {
			int middle = min + (max - min) / 2;
			mergeSortHelper(data, min, middle);
			mergeSortHelper(data, middle + 1, max);
			merge(data, min, middle, middle + 1, max);
		} else
			return;
	}

	private void merge(LinkedList<E> data, int min1, int max1, int min2, int max2) {
		LinkedList<E> newList = new LinkedList<>();
		int counter1 = min1, counter2 = min2;

		while (!(counter1 > max1) && !(counter2 > max2)) {
			if (data.get(counter1).compareTo(data.get(counter2)) < 0)
				newList.add(data.get(counter1++));
			else
				newList.add(data.get(counter2++));
		}
		
		if (counter1 > max1)
			mergeHelper(data, counter2, max2, newList);
		else
			mergeHelper(data, counter1, max1, newList);

		while (!(min1 > max2))
			data.set(min1++, newList.removeFirst());
	}

	private void mergeHelper(LinkedList<E> data, int counter, int max, LinkedList<E> newList) {
		while (!(counter > max))
			newList.add(data.get(counter++));
	}

	public void printList(LinkedList<E> data) {
		data.forEach((temp) -> {
			System.out.println(temp);
		});
	}

	public void printListForTest(LinkedList<E> data) {
		data.forEach((temp) -> {
			System.out.print(temp);
		});
	}

	public static void main(String[] args) {
		
		final long timeStart = System.currentTimeMillis();

		LinkedList<Integer> list = new LinkedList<>();
		Random r = new Random();
		for (int i = 0; i < 20000; i++) {
			list.add(1 + r.nextInt(1000));
		}
		MergeSortSequenziell<Integer> ms1 = new MergeSortSequenziell<>();

		try {
			ms1.mergeSort(list);
			ms1.printList(list);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		final long timeEnd = System.currentTimeMillis();
		System.out.println("Verlaufszeit der Schleife: " + (timeEnd - timeStart) + " Millisek.");
	}

}
