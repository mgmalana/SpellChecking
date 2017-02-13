package tokenizer;

public class Tokenizer {

	public static String[] tokenize(String input) {

		String[] delimiters = { ".", ",", "!" };

		for (String mark : delimiters)
			input = input.replace(mark, " " + mark);

		return input.split(" ");
	}
}
