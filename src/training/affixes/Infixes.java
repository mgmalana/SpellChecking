package training.affixes;

import java.util.LinkedHashMap;

public class Infixes {

	private LinkedHashMap<String, String> patternReplace;
	private final String consonant = "(^[^aeiou])";
	private final String vowel = "[aeiou]";

	public Infixes() {
		patternReplace = new LinkedHashMap<String, String>();

		patternReplace.put(consonant + "um" + "(" + vowel + ".*)", "$1$2");
		patternReplace.put(consonant + "in" + "(" + vowel + ".*)", "$1$2");
	}

	public LinkedHashMap<String, String> getInfixes() {
		return patternReplace;
	}
}
