package training;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import main.Configuration;
import training.affixes.Prefixes;
import training.affixes.Suffixes;

public class Stemmer {

	File file = null;
	private LinkedHashMap<String, String> prefixList = null;
	private HashMap<String, String> suffixList = null;
	private Collection<String> prefixListValues = null;

	public Stemmer(Prefixes prefixList, Suffixes suffixList) {
		this.prefixList = prefixList.getPrefixes();
		this.suffixList = suffixList.getSuffixes();
		this.prefixListValues = this.prefixList.values();
	}

	public LinkedHashSet<String> stemWordList(Set<String> wordList) {
		LinkedHashSet<String> stemmedList = new LinkedHashSet<>();

		for (String inflected : wordList)
			stemmedList.add(stemming(inflected));

		return stemmedList;
	}

	public String stemming(String input) {

		if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
			return input;

		for (String pattern : prefixList.keySet()) {
			String temp = input;
			if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
				return input;

			if (input.matches((pattern))) {
				input = input.replaceFirst(prefixList.get(pattern), "");

			}

			// Return 'yung OLD STRING kapag AFTER STEMMING ay naging less than
			// MINIMUM_WORD_LENGTH ito
			if (input.length() < Configuration.MINIMUM_WORD_LENGTH) {
				input = temp;
			}

		}

		for (String pattern : suffixList.keySet()) {

			if (input.length() <= Configuration.MINIMUM_WORD_LENGTH)
				return input;

			if (input.matches((pattern))) {
				String temp = input;
				String suffixValue = suffixList.get(pattern);
				String[] findReplace = null;

				if (suffixValue.contains("_")) {
					findReplace = new String[2];
					findReplace = suffixValue.split("_");
				} else {
					findReplace = new String[1];
					findReplace[0] = suffixValue;
				}
				switch (findReplace.length) {
				case 2:
					input = input.replaceFirst(findReplace[0], findReplace[1]);
					break;
				case 1:
					input = input.replaceFirst(findReplace[0], "");
					break;
				}

				// Return 'yung OLD STRING kapag AFTER STEMMING ay naging less
				// than 4 ito
				if (input.length() <= Configuration.MINIMUM_WORD_LENGTH) {
					input = temp;
				}

			}
		}

		return input;
	}

	public boolean isPrefix(String prefix) {

		for (String prefixWord : prefixListValues)
			if (prefix.contains(prefixWord))
				return true;

		return false;
	}
}
