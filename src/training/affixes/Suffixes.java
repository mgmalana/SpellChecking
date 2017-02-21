package training.affixes;

import java.util.LinkedHashMap;

public class Suffixes {

	private final String consonant_r = "[^aeiour]";
	private final String consonant = "[^aeiou]";
	private final String vowel = "[aeiou]";

	LinkedHashMap<String, String> patternReplace = null;

	public Suffixes() {
		patternReplace = new LinkedHashMap<>();

		// assimilation
		patternReplace.put(".*uhin$", "uhin_o");
		patternReplace.put(".*uhan$", "uhan_o");
		patternReplace.put(".*uan$", "uan_o");
		patternReplace.put(".*uin$", "uin_o");

		patternReplace.put(".*[aeio]hin$", "hin");
		patternReplace.put(".*[aeio]han$", "han");

		// assimilation
		patternReplace.put(".*rin$", "rin_d");
		patternReplace.put(".*ran$", "ran_d");

		patternReplace.put(".*" + vowel + consonant_r + "in$", "in");
		patternReplace.put(".*" + vowel + consonant_r + "an$", "an");
		patternReplace.put(".*" + "ngin$", "in");
		patternReplace.put(".*" + "ngan$", "an");
	}

	public LinkedHashMap<String, String> getSuffixes() {
		return patternReplace;
	}
}
