package training.affixes;

import java.util.LinkedHashMap;

public class Suffixes {

	private final String consonant_r = "[^aeiour]";
	LinkedHashMap<String, String> patternReplace = null;

	public Suffixes() {
		patternReplace = new LinkedHashMap<>();

		// assimilation
		patternReplace.put(".*uhin$", "uhin_o");
		patternReplace.put(".*uhan$", "uhan_o");

		patternReplace.put(".*[aeio]hin$", "hin");
		patternReplace.put(".*[aeio]han$", "han");

		// assimilation
		patternReplace.put(".*rin$", "rin_d");
		patternReplace.put(".*ran$", "ran_d");

		patternReplace.put(".*" + consonant_r + "in$", "in");
		patternReplace.put(".*" + consonant_r + "an$", "an");
	}

	public LinkedHashMap<String, String> getSuffixes() {
		return patternReplace;
	}
}
