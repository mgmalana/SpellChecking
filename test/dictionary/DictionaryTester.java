package dictionary;

import java.util.LinkedHashSet;

import training.IOFile;
import utlity.Configuration;

public class DictionaryTester {

	private static LinkedHashSet<String> english;
	private static LinkedHashSet<String> filipino;
	private static LinkedHashSet<String> intersection = null;

	public static void main(String[] args) {
		IOFile ioFile = new IOFile();

		english = ioFile.readResource(Configuration.ENGLISH_DICT);
		filipino = ioFile.readResource(Configuration.FILIPINO_DICT);
		intersection = new LinkedHashSet<>();

		int size = filipino.size();
		int counter = 0;
		LinkedHashSet<String> intersection = new LinkedHashSet<>();

		for (String word : filipino) {
			counter++;
			if (english.contains(word))
				intersection.add(word);

			if (counter % 100 == 0) {
				double perc = (double) counter / (double) size;
				System.out.println(perc);
			}
		}
		System.out.println("intersection size: " + intersection.size());

	}

}
