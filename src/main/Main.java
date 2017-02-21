package main;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.BoxOfC.LevenshteinAutomaton.LevenshteinAutomaton;
import com.BoxOfC.MDAG.MDAG;

import corrector.MergeCorrection;
import corrector.SplitCorrection;
import detector.DictLookUp;
import detector.NGramStatistics;
import tokenizer.Tokenizer;
import training.DAWG;
import training.IOFile;
import training.LanguageModel;
import training.Stemmer;
import training.affixes.Infixes;
import training.affixes.PartialReduplication;
import training.affixes.Prefixes;
import training.affixes.Suffixes;
import utlity.Configuration;
import utlity.Logger;

public class Main {

	public static void main(String[] args) {
		IOFile ioFile = new IOFile();
		Stemmer stemmer = null;

		Prefixes prefixList = new Prefixes();
		Suffixes suffixList = new Suffixes();

		Infixes infixList = null;
		PartialReduplication redupRuleList = null;

		if (Configuration.LIGHT_STEMMER)
			stemmer = new Stemmer(prefixList, suffixList);
		else {
			infixList = new Infixes();
			redupRuleList = new PartialReduplication();
			stemmer = new Stemmer(prefixList, suffixList, infixList, redupRuleList);
		}
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

		// ERROR CORRECTION VARIABLES
		SplitCorrection splitCorrector = new SplitCorrection(filiDict);
		MergeCorrection mergeCorrector = new MergeCorrection(stemmer);
		LinkedHashSet<String> candidateSuggestions = null;

		Logger logger = new Logger();
		int sentenceNumber = 0;

		// TEST SENTENCE
		ArrayList<String> document = new ArrayList<>();
		document.add("Ang mga bata na magaganda.*#");
		document.add("Sila rin ay bata na magaganda.*#");
		document.add("mag laro ay palang na magaganda.*#");
		document.add("Mg-texts laro Magelan ay magagandangbabae na magaganda.*#");

		Tokenizer tokenizer = new Tokenizer();
		for (String sentence : document) {
			sentenceNumber++;

			// TOKENIZATION OF INPUT SENTENCES
			String[] words = tokenizer.tokenize(sentence);

			// words.length-1 = remove the marker '*#'
			for (int wordCounter = 0; wordCounter < words.length - 1; wordCounter++) {

				// GET CURRENT WORD IN THE SENTENCE
				String cWord = words[wordCounter];
				candidateSuggestions = new LinkedHashSet<>();
				
				// SKIP IF THE WORD IS A PUNCTUATION MARKS
				if (tokenizer.isDelimeters(cWord) || tokenizer.isProperNoun(cWord)) {
					if (Configuration.LOGGER) {
						
						candidateSuggestions.add("Delimeter / ProperNoun");
						logger.log(sentenceNumber, sentence, cWord, false, candidateSuggestions);
					}
					continue;
				}
				// ---------------
				// ---------------
				// ERROR DETECTION
				// ---------------
				// ---------------

				// WORD ITSELF: DICTIONARY LOOKUP
				boolean inDictionary = dictLookUp.checkDict(cWord);

				// CODE-SWTICHING CASE: GET THE STEM OF THE WORD AND DICTIONARY
				// LOOK-UP
				if (!inDictionary && cWord.contains("-")) {
					// STEM OF THE CODE-SWITCHING
					String[] codeSwitchWord = cWord.split("-");

					boolean isPrefix = stemmer.isPrefix(codeSwitchWord[0]);
					boolean isEngWord = dictLookUp.checkEngDict(codeSwitchWord[1]);

					if (isEngWord && !isPrefix) {
						if (Configuration.LOGGER) {
							candidateSuggestions.add("Wrong prefix of the word");
							logger.log(sentenceNumber, sentence, cWord, inDictionary, candidateSuggestions);
						}
						continue;
					}
					inDictionary = (isPrefix && isEngWord);
				}

				// INFLECTED CASE: GET THE STEM OF THE WORD AND DICTIONARY
				// LOOK-UP
				if (!inDictionary) {
					// STEM OF THE FILIPINO WORD
					String word = stemmer.stemming(cWord);
					inDictionary = dictLookUp.checkFiliDict(word);
				}

				// NO WORD IN DICTIONARY: N-GRAM
				if (!inDictionary && !stemmer.isPrefix(cWord)) {
					inDictionary = nGramStats.hasHighNGramStatistics(cWord);
				}

				// ---------------
				// ---------------
				// ERROR CORRECTION FOR
				// THE MISPPLED WORD
				// ---------------
				// ---------------

				String nWord = words[wordCounter + 1];
				
				if (!inDictionary) {
					// SPLIT CORRECTION
					candidateSuggestions = splitCorrector.splitSuggestion(cWord);

					// MERGE CORRECTION
					String mergeCorrectionSuggestion = mergeCorrector.setConsecutiveWords(cWord, nWord);
					if (!mergeCorrectionSuggestion.equals("")) {
						wordCounter++;
						candidateSuggestions.add(mergeCorrectionSuggestion);
					}

					// AUTOMATON CORRECTION
					if (candidateSuggestions.size() == 0) {
						candidateSuggestions.addAll(LevenshteinAutomaton.iterativeFuzzySearch(2, cWord, filiDict));
					}
				}

				if (Configuration.LOGGER)
					logger.log(sentenceNumber, sentence, cWord, inDictionary, candidateSuggestions);
			}
		}

		ioFile.trainResource(Configuration.LOG_FILE, Configuration.OVERWRITE_FILE, logger.getLog());
	}

}
