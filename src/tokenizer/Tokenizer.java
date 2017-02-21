package tokenizer;

public class Tokenizer {

	String[] delimiters = { ".", ",", "!", "*#" };

	public String[] tokenize(String input) {

		for (String mark : delimiters)
			input = input.replace(mark, " " + mark);

		return input.toLowerCase().split(" ");
	}

	public boolean isDelimeters(String word) {

		for (String delimiter : delimiters) {
			if (delimiter.equals(word))
				return true;
		}
		return false;
	}

}
