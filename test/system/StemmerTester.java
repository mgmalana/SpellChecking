package system;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import training.IOFile;
import training.Stemmer;
import training.affixes.Infixes;
import training.affixes.PartialReduplication;
import training.affixes.Prefixes;
import training.affixes.Suffixes;
import utlity.Configuration;

public class StemmerTester {

	public static void testStemmer() {
		Stemmer stemmer = null;

		Prefixes prefixList = new Prefixes();
		Suffixes suffixList = new Suffixes();

		Infixes infixList = null;
		PartialReduplication redup = null;

		int counter = 0;
		if (Configuration.LIGHT_STEMMER)
			stemmer = new Stemmer(prefixList, suffixList);
		else {
			infixList = new Infixes();
			redup = new PartialReduplication();
			stemmer = new Stemmer(prefixList, suffixList, infixList, redup);
		}
		HashMap<String, String> wordStem = AffixesTestList.testSmallData();

		for (String word : wordStem.keySet()) {
			String stemmed = stemmer.stemming(word);

			if (stemmed.equalsIgnoreCase(wordStem.get(word))) {
				++counter;
			} else {
				System.out.println("orig: " + word + " stemmed: " + stemmed);
			}
		}
		double score = ((double) counter / (double) wordStem.size()) * 100;

		System.out.println("------");
		System.out.println("Test 1: Stemming");
		System.out.println(score + "%");
		System.out.println("------");

		IOFile ioFile = new IOFile();
		Set<String> stemmedList = stemmer.stemWordList(wordStem.keySet());

		/**
		 * STORE GENERATED STEMMED WORD LIST
		 */
		ioFile.writeResource(Configuration.STEMMED_FILE, stemmedList);
		String lastModified = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss")
				.format(new Date(new File(ioFile.getResource() + Configuration.STEMMED_FILE).lastModified()));

		System.out.println();
		System.out.println("Test 2: Storing the stemmed list");
		System.out.println(lastModified);
		System.out.println("------");
	}

}
