package training.affixes;

public class CustomizedRule {

	private String word = "";
	private String vowel = "[aeiou]";
	private String consonant = "[^aeiou]";

	// D-R alternation
	private String pattern = "(.*d" + vowel + ")r(" + vowel + ".*)";

	public void testCustomeRules(String word) {
		this.word = word;
		testCustomizedRule();
	}

	private void testCustomizedRule() {
		String temp = word;

		// mapansin = pansin
		if (word.matches("^mapa.*") && word.matches(".*[an|in]$")) {
			this.word = word.substring(2);
		}
		// mainom = inom
		// nainit = init
		else if ((word.startsWith("mai") || word.startsWith("nai"))
				&& word.matches("..." + consonant + vowel + consonant)) {

			this.word = word.substring(2);
		}

		else if (word.matches("^mai" + consonant + consonant + ".*")) {
			this.word = word.substring(2);
		}

		// makain = kain
		else if (word.startsWith("maka") && word.substring(4).length() <= 3)
			this.word = word.substring(2);

		// iakyat = akyat
		// ibihis = bihis AND ipon = ipon
		else if ((word.matches("^i" + vowel + ".*"))
				|| (word.matches("^i" + consonant + ".*") && !(word.substring(1).length() <= 3))) {
			this.word = word.substring(1);

		}

		// darasal = dadasal
		else if (word.matches(pattern)) {
			this.word = word.replaceFirst(pattern, "$1d$2");
		}

		// umubo = ubo
		else if (word.matches("^um" + vowel + consonant + vowel))
			this.word = word.substring(2);
		
		else if(word.matches(".*ng"))
			this.word = word.substring(0, word.length());
	}

	public String changeU2O(String word) {
		String pattern = "(.*)u(" + consonant + ")$";
		if (word.matches(pattern)) {
			return word.replaceFirst(pattern, "$1o$2");
	
		}
		return "";
	}

	public String getWord() {
		return word;
	}
}
