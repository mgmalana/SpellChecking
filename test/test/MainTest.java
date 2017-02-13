package test;

public class MainTest {
	
	
	public static void main(String[] args){
		
		System.out.println("STEMMER");
		StemmerTester.testStemmer();
		System.out.println();
		System.out.println("LANGUAGE MODEL");
		LangModelTester.testGenerateLangModel();
		
	}

}
