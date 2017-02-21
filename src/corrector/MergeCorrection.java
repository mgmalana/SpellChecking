package corrector;

import training.Stemmer;

public class MergeCorrection {

	private Stemmer stemmer;

	public MergeCorrection(Stemmer stemmer) {
		this.stemmer = stemmer;
	}

	public String setConsecutiveWords(String word1, String word2) {
		String mergeWord = "";
		// *# = end of line
		if ((!word2.equals("*#")) && stemmer.isPrefix(word1)) {
			// word1 ENDS WITH = ".*[pmn]ag" and word2 STARTS WITH = [aeiou]
			if (word1.endsWith("ag") && word2.matches("[aeiou].*")) {
				mergeWord = word1 + "-" + word2;
				// word1 ENDS WITH = ".*[pmn]ag" and word2 STARTS WITH =
				// consonants
			} else if (word1.endsWith("ag") && word2.matches("[a-z&&[^aeiou]].*")) {
				mergeWord = word1 + word2;
			}
		}
		return mergeWord;
	}
}
