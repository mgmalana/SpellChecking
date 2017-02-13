package test;

import java.util.HashMap;

public class AffixesTestList {
	public static HashMap<String, String> testSuffix() {
		HashMap<String, String> wordStem = new HashMap<>();
		wordStem.put("punuhin", "puno");
		wordStem.put("gantihan", "ganti");
		wordStem.put("tawiran", "tawid");
		wordStem.put("anan", "anan");
		return wordStem;
	}

	public static HashMap<String, String> testPrefix() {
		HashMap wordStem = new HashMap<>();
		wordStem.put("mag-asawa", "asawa");
		wordStem.put("magbalik", "balik");
		wordStem.put("pambansa", "bansa");
		wordStem.put("papagkatao", "tao");
		return wordStem;

	}
}
