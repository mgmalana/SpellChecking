package training.affixes;

import utlity.Configuration;

public class PartialReduplication {

	private String word;
	private final String vowel = "[aeiou]";
	private final String consonant = "[^aeiou]";
	private String tempWord;

	public void testPartialRedup(String word) {
		try {
			this.word = word;
			this.tempWord = word;

			if (!(word.length() <= Configuration.MINIMUM_WORD_LENGTH)) {
				if (word.contains("-")) {
					String[] split = word.split("-");

					// reduplicated and hyphenated from the stem.
					if (split[0].length() != split[1].length() && split[1].contains(split[0])) {
						this.word = split[1];
					}
				}

				else if (word.charAt(0) == word.charAt(1))
					redupRule1();
				else if (word.matches(consonant + vowel + ".*") && (word.substring(0, 2).equals(word.substring(2, 4))))
					redupRule2();
				else if (word.matches(consonant + vowel + consonant + consonant + vowel + ".*")
						&& (word.substring(0, 2).equals(word.charAt(2) + "" + word.charAt(4))))
					redupRule3();
				else if (word.matches(consonant + consonant + vowel + consonant + consonant + vowel + ".*")
						&& (word.substring(0, 3).equals(word.substring(3, 6))))
					redupRule4();
			}
		} catch (IndexOutOfBoundsException exception) {
			this.word = tempWord;
		}
	}

	/*
	 * Reduplicates the cluster of consonants including the succeeding vowel of
	 * the stem.
	 */
	private void redupRule4() {
		word = word.substring(3);
	}

	/* If the first syllable of the root has a cluster of consonants, */
	private void redupRule3() {
		word = word.substring(2);
	}

	/*
	 * In a two-syllable root, if the first syllable of the stem starts with a
	 * consonant- vowel, the consonant and the succeeding vowel is reduplicated.
	 */
	private void redupRule2() {
		word = word.substring(2);
	}

	/*
	 * If the root of a two-syllable word begins with a vowel, the initial
	 * letter is repeated.
	 */
	private void redupRule1() {
		word = word.substring(1);
	}

	public String getPartialRedup() {
		if (word.length() < Configuration.MINIMUM_WORD_LENGTH)
			return tempWord;
		else
			return word;
	}

}
