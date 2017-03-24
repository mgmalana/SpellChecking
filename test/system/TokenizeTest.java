package system;

import utlity.Tokenizer;

public class TokenizeTest {

	public static void tokenize(String input) {

		Tokenizer tokenizer = new Tokenizer();
		

		String[] data = tokenizer.tokenize(input);

		for (String word : data)
			System.out.print(word + " ");
	}
}
