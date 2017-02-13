package detector;

import com.BoxOfC.MDAG.MDAG;

import main.Configuration;

public class DictLookUp {

	private MDAG engDict = null;
	private MDAG filiDict = null;

	public DictLookUp(MDAG engDict, MDAG filiDict) {
		this.engDict = engDict;
		this.filiDict = filiDict;
	}

	/**
	 * @param word
	 *            - the word to be checked in English dictionary
	 */
	public boolean checkEngDict(String word) {
		return engDict.contains(word);
	}

	/**
	 * @param word
	 *            - the word to be checked in Filipino dictionary
	 */
	public boolean checkFiliDict(String word) {
		return filiDict.contains(word);
	}

	/**
	 * @param word
	 *            - the word to be checked in Filipino and English
	 *            dictionary,respectively.
	 */
	public boolean checkDict(String word) {

		// ALL WORDS WITH LENGTH < 3 ARE CONSIDERED CORRECT
		if ((word.length() < Configuration.MINIMUM_WORD_LENGTH))
			return true;

		boolean inDictionary = false;
		inDictionary = checkFiliDict(word);
		if (!inDictionary)
			inDictionary = checkEngDict(word);

		return inDictionary;
	}
}
