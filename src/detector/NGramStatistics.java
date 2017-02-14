package detector;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import main.Configuration;
import training.IOFile;

public class NGramStatistics {

	IOFile ioFile = new IOFile();
	private int nGram = Configuration.nGram;

	private LinkedHashSet<String> nGramList = null;
	private Map<String, Double> nGramFreq = null;

	public NGramStatistics(String nGramFile) {

		nGramList = ioFile.readResource(nGramFile);
		generateNGramFreq();
	}

	private void generateNGramFreq() {

		nGramFreq = new LinkedHashMap<>();

		int counter = 0;
		int topNGram = (int) (Configuration.nGramThreshold * nGramList.size());
		for (String wordFreq : nGramList) {
			if (counter > topNGram)
				break;

			String[] pair = wordFreq.split("\t");
			nGramFreq.put(pair[0], Double.parseDouble(pair[1]));
			counter++;
		}

	}

	public boolean hasHighNGramStatistics(String word) {

		boolean highNGramStat = true;
		if (!(word.length() < Configuration.MINIMUM_WORD_LENGTH)) {

			String cWord = "_" + word + "_";
			for (int counter = nGram; counter < word.length() + 1; counter++) {
				String gram = cWord.substring(counter - nGram, counter);

				if (Configuration.VERBOSE)
					System.out.println("word: " + gram + " in gramFreq " + nGramFreq.containsKey(gram) + " value: "
							+ nGramFreq.get(gram));

				if (!nGramFreq.containsKey(gram)) {
					return false;
				}
			}

		}
		return highNGramStat;
	}

	public void displayNGram() {
		System.out.println("ngramList: " + nGramList.size());
		System.out.println("ngramFreq: " + nGramFreq.size());
		int counter = 0;
		for (String word : nGramFreq.keySet()) {
			counter++;
			System.out.println(counter + " last value: " + word + " -  " + nGramFreq.get(word));
		}

	}
}
