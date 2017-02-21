package tokenizer;

public class Tokenizer {

	String[] delimiters = { ".", ",", "!", "*#" };

	public String[] tokenize(String input) {

		for (String mark : delimiters)
			input = input.replace(mark, " " + mark);

		String[] tokenized = input.split(" ");
		tokenized[0] = tokenized[0].toLowerCase();

		return tokenized;
	}

	public boolean isDelimeters(String word) {

		for (String delimiter : delimiters) {
			if (delimiter.equals(word))
				return true;
		}
		return false;
	}

	public boolean isProperNoun(String word) {
		String smallAlpha = "^[a-z]+$";

		// CAPITAL LETTER
		if (word.charAt(0) != word.toLowerCase().charAt(0))
			return true;

		for (int counter = 0; counter < word.length(); counter++)
			if (!Character.isAlphabetic(word.charAt(counter)) && (word.charAt(counter) != '-'))
				return true;

		return false;
	}

}
