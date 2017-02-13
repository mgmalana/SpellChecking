package training;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LanguageModel {

	private String fileName = null;
	private int nGram;
	private IOFile ioFile = new IOFile();
	private Set<String> wordList = null;

	public LanguageModel(String fileName, int nGram) {
		this.fileName = fileName;

		if (nGram >= 3 && nGram <= 5)
			this.nGram = nGram;
		else {
			System.out.println("ADJUSTING TO MINUMUM/MAXIMUM VALUE");
			if (nGram < 3)
				this.nGram = 3;
			else
				this.nGram = 5;
		}
	}

	public LanguageModel(Set<String> stemmedList, int nGram) {
		this.wordList = stemmedList;
		if (nGram >= 3 && nGram <= 5)
			this.nGram = nGram;
		else {
			System.out.println("ADJUSTING TO MINUMUM/MAXIMUM VALUE");
			if (nGram < 3)
				this.nGram = 3;
			else
				this.nGram = 5;
		}
	}

	public Set<String> generateNGram() {
		Set<String> nGramFreq = new HashSet<>();

		HashMap<String, Integer> keyValue = new HashMap<>();
		Set<String> stemmedList = null;

		if (fileName == null)
			stemmedList = wordList;
		else
			stemmedList = ioFile.readResource(fileName);

		for (String word : stemmedList) {
			if (!(word.length() < 3)) {
				String cWord = "_" + word + "_";

				for (int counter = nGram; counter < word.length() + 1; counter++) {
					String gram = cWord.substring(counter - nGram, counter);

					if (!keyValue.containsKey(gram))
						keyValue.put(gram, 1);
					else
						keyValue.put(gram, keyValue.get(gram) + 1);
				}
			}
		}

		for (String key : keyValue.keySet())
			nGramFreq.add(key + "\t" + keyValue.get(key));

		return nGramFreq;
	}
}
