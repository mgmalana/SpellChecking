package system;

public class MainTest {

	public static void main(String[] args) {

		String input = "ang mga bata na magagaling, na.";

		System.out.println("STEMMER");
		 StemmerTester.testStemmer();
		System.out.println();
		System.out.println("LANGUAGE MODEL");
		// LangModelTester.testGenerateLangModel();
		//TokenizeTest.tokenize(input);

	}

}
