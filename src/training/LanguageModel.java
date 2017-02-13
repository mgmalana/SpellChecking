package training;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import main.Configuration;

public class LanguageModel {

	private String fileName = null;
	private int nGram;
	private IOFile ioFile = new IOFile();
	private Set<String> wordList = null;

	public LanguageModel(String fileName, int nGram) {
		this.fileName = fileName;

		if (nGram >= Configuration.MINIMUM_WORD_LENGTH && nGram <= Configuration.MAXIMUM_WORD_LENGTH)
			this.nGram = nGram;
		else {
			System.out.println("ADJUSTING TO MINUMUM/MAXIMUM VALUE");
			if (nGram < Configuration.MINIMUM_WORD_LENGTH)
				this.nGram = Configuration.MINIMUM_WORD_LENGTH;
			else
				this.nGram = Configuration.MAXIMUM_WORD_LENGTH;
		}
	}

	public LanguageModel(Set<String> stemmedList, int nGram) {
		this.wordList = stemmedList;
		if (nGram >= Configuration.MINIMUM_WORD_LENGTH && nGram <= Configuration.MAXIMUM_WORD_LENGTH)
			this.nGram = nGram;
		else {
			System.out.println("ADJUSTING TO MINUMUM/MAXIMUM VALUE");
			if (nGram < Configuration.MINIMUM_WORD_LENGTH)
				this.nGram = Configuration.MINIMUM_WORD_LENGTH;
			else
				this.nGram = Configuration.MAXIMUM_WORD_LENGTH;
		}
	}

	public Set<String> generateNGram() {
		Set<String> nGramFreq = new HashSet<>();

		HashMap<String, Integer> keyValue = new HashMap<>();
		Set<String> stemmedList = null;
		int normalizer = 0;

		if (fileName == null)
			stemmedList = wordList;
		else
			stemmedList = ioFile.readResource(fileName);

		for (String word : stemmedList) {
			if (!(word.length() < Configuration.MINIMUM_WORD_LENGTH)) {
				String cWord = "_" + word + "_";

				for (int counter = nGram; counter < word.length() + 1; counter++) {
					normalizer++;
					String gram = cWord.substring(counter - nGram, counter);

					if (!keyValue.containsKey(gram))
						keyValue.put(gram, 1);
					else
						keyValue.put(gram, keyValue.get(gram) + 1);
				}
			}
		}

		for (String key : keyValue.keySet()) {
			double normalizedScore = (double) keyValue.get(key) / normalizer;
			nGramFreq.add(key + "\t" + normalizedScore);
		}
		return nGramFreq;
	}
}
