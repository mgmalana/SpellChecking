package detector;

import java.util.HashMap;
import java.util.HashSet;

import main.Configuration;
import training.IOFile;

public class NGramStatistics {

	IOFile ioFile = new IOFile();
	private int nGram = Configuration.nGram;

	private HashSet<String> nGramList = null;
	private HashMap<String, Double> nGramFreq = null;

	public NGramStatistics(String nGramFile) {

		nGramList = ioFile.readResource(nGramFile);
		generateNGramFreq();
	}

	private void generateNGramFreq() {

		nGramFreq = new HashMap<>();

		for (String wordFreq : nGramList) {

			String[] pair = wordFreq.split("\t");
			nGramFreq.put(pair[0], Double.parseDouble(pair[1]));
		}
		nGramList = null;
	}

	public boolean hasHighNGramStatistics(String word) {

		boolean highNGramStat = true;
		if (!(word.length() < Configuration.MINIMUM_WORD_LENGTH)) {
			Double frequency = 0.0;

			String cWord = "_" + word + "_";
			for (int counter = nGram; counter < word.length() + 1; counter++) {
				String gram = cWord.substring(counter - nGram, counter);

				if (nGramFreq.containsKey(gram)) {
					frequency = nGramFreq.get(gram);
					if (frequency < Configuration.nGramThreshold)
						return false;
				}
			}

		}
		return highNGramStat;
	}
}
