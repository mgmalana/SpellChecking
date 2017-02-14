package main;

import java.util.HashSet;
import java.util.LinkedHashSet;

import com.BoxOfC.MDAG.MDAG;

import detector.DictLookUp;
import detector.NGramStatistics;
import tokenizer.Tokenizer;
import training.DAWG;
import training.IOFile;
import training.LanguageModel;
import training.Stemmer;
import training.affixes.Prefixes;
import training.affixes.Suffixes;

public class Main {

	public static void main(String[] args) {
		IOFile ioFile = new IOFile();
		Prefixes prefixList = new Prefixes();
		Suffixes suffixList = new Suffixes();
		Stemmer stemmer = new Stemmer(prefixList, suffixList);

		// GET FILIPINO DICT
		LinkedHashSet<String> dictionary = ioFile.readResource(Configuration.FILIPINO_DICT);

		// GET THE STEM OF EACH WORD
		dictionary = stemmer.stemWordList(dictionary);

		// STORE GENERATED STEMMED WORD LIST
		ioFile.trainResource(Configuration.STEMMED_FILE, Configuration.OVERWRITE_FILE, dictionary);

		// GET STEMMED LANGUAGE MODEL
		LanguageModel languageModel = new LanguageModel(dictionary, Configuration.nGram);
		dictionary = languageModel.generateNGram();

		// STORE GENERATED N-GRAM LIST
		ioFile.trainResource(Configuration.NGRAM_FILE, Configuration.OVERWRITE_FILE, dictionary);
		dictionary = null;

		// CREATE AUTOMATON REPRESENTATION OF DICTIOANRY
		MDAG engDict = DAWG.dictAutomaton(Configuration.ENGLISH_DICT);
		MDAG filiDict = DAWG.dictAutomaton(Configuration.FILIPINO_DICT);

		// DICTIONARY LOOK-UP AND N-GRAM APPROACH
		DictLookUp dictLookUp = new DictLookUp(engDict, filiDict);
		NGramStatistics nGramStats = new NGramStatistics(Configuration.NGRAM_FILE);

		// TEST SENTENCE
		String[] document = new String[1];
		document[0] = "ang mag-eiders magsdf";

		Tokenizer tokenizer = new Tokenizer();
		for (String sentence : document) {

			// TOKENIZATION OF INPUT SENTENCES
			String[] words = tokenizer.tokenize(sentence);

			for (int counter = 0; counter < words.length; counter++) {

				// GET CURRENT WORD IN THE SENTENCE
				String cWord = words[counter];
				String word = "";

				// SKIP IF THE WORD IS A PUNCTUATION MARKS
				if (tokenizer.isDelimeters(cWord))
					continue;

				// WORD ITSELF: DICTIONARY LOOKUP
				boolean inDictionary = dictLookUp.checkDict(cWord);

				// NEXT WORD IF THE cWord IS FOUND IN THE DICTIONARY
				if (inDictionary)
					continue;

				// CODE-SWTICHING CASE: GET THE STEM OF THE WORD AND DICTIONARY
				// LOOK-UP
				if (!inDictionary && cWord.contains("-")) {
					// STEM OF THE CODE-SWITCHING
					String[] codeSwitchWord = cWord.split("-");

					boolean isPrefix = stemmer.isPrefix(codeSwitchWord[0]);
					boolean isEngWord = dictLookUp.checkEngDict(codeSwitchWord[1]);

					if (isEngWord && !isPrefix) {
						System.out.println("Wrong prefix of the word");
						continue;
					}
					inDictionary = (isPrefix && isEngWord);
				}

				// INFLECTED CASE: GET THE STEM OF THE WORD AND DICTIONARY
				// LOOK-UP
				if (!inDictionary) {
					// STEM OF THE FILIPINO WORD
					word = stemmer.stemming(cWord);
					inDictionary = dictLookUp.checkFiliDict(word);

				}

				// NO WORD IN DICTIONARY: N-GRAM
				if (!inDictionary) {
					System.out.println("here");
					inDictionary = nGramStats.hasHighNGramStatistics(cWord);
				}
			}
		}
	}

}
