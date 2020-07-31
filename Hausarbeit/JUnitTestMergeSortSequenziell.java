package pp;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.junit.jupiter.api.Test;

class JUnitTestMergeSortSequenziell {

	MergeSortSequenziell<String> ms1 = new MergeSortSequenziell<>();
	MergeSortSequenziell<Integer> ms2 = new MergeSortSequenziell<>();

	@Test
	void freeTypeTest() {

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		LinkedList<Integer> list = new LinkedList<>();
		ms2.mergeSort(list);

		ms2.printListForTest(list);
		String expectedOutput = "";
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	void StringsDescending() {

		LinkedList<String> list = new LinkedList<>();
		list.add("Zaan");
		list.add("Xenon");
		list.add("Walter");
		list.add("Vadim");
		list.add("Ullrich");
		list.add("Ted");
		list.add("Sam");
		list.add("Rob");
		list.add("Pedro");
		list.add("Otis");
		list.add("Niklas");
		list.add("Matt");
		list.add("Lion");
		list.add("Kai");
		list.add("Jessy");
		list.add("Ivan");
		list.add("Hendrik");
		list.add("Hanno");
		list.add("Georg");
		list.add("Frodo");
		list.add("Egon");
		list.add("Diego");
		list.add("Derek");
		list.add("Damir");
		list.add("Carlo");
		list.add("Bryson");
		list.add("Bo");
		list.add("Baal");
		list.add("August");
		list.add("Andrea");
		list.add("Adam");
		list.add("Aaron");
		ms1.mergeSort(list);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		ms1.printListForTest(list);
		String expectedOutput = "AaronAdamAndreaAugustBaalBoBrysonCarloDamirDerekDiegoEgonFrodoGeorgHannoHendrikIvanJessyKaiLionMattNiklasOtisPedroRobSamTedUllrichVadimWalterXenonZaan";
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	void randomNumbers() {

		LinkedList<Integer> mylist = new LinkedList<>();
		LinkedList<Integer> expectedlist = new LinkedList<>();
		Random r = new Random();
		for (int i = 0; i < 132; i++) {
			int randomNumber = 1 + r.nextInt(1000);
			mylist.add(randomNumber);
			expectedlist.add(randomNumber);
		}

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		ms2.mergeSort(mylist);
		ms2.printList(mylist);

		ByteArrayOutputStream expectedOutput = new ByteArrayOutputStream();
		System.setOut(new PrintStream(expectedOutput));
		Collections.sort(expectedlist);
		ms2.printList(expectedlist);

		assertEquals(expectedOutput.toString(), outContent.toString());
	}

}

