package main;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.BoxOfC.LevenshteinAutomaton.LevenshteinAutomaton;
import com.BoxOfC.MDAG.MDAG;

import corrector.MergeCorrection;
import corrector.SplitCorrection;
import detector.DictLookUp;
import detector.NGramStatistics;
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
import utlity.Tokenizer;

/**
 * 
 * The controller of the spell checking system.
 * 
 **/
public class Main {

	public static void main(String[] args) {

		/*** resource controller (read/write). */
		IOFile ioFile = new IOFile();
		/*** stemmer of the system. */
		Stemmer stemmer = null;

		/*** list of pre-defined prefixes in the system */
		Prefixes prefixList = new Prefixes();
		/*** list of pre-defined suffixes in the system */
		Suffixes suffixList = new Suffixes();

		/*** list of pre-defined suffixes in the system */
		Infixes infixList = null;
		/*** list of pre-defined partial reduplication rules in the system */
		PartialReduplication redupRuleList = null;

		// Check what stemmer will be used in the system.
		if (Configuration.LIGHT_STEMMER) {
			// LIGHT_STEMMER = TRUE in the configuration
			stemmer = new Stemmer(prefixList, suffixList);
		} else {
			// LIGHT_STEMMER = FALSE in the configuration
			infixList = new Infixes();
			redupRuleList = new PartialReduplication();
			stemmer = new Stemmer(prefixList, suffixList, infixList, redupRuleList);
		}

		/*** Filipino dictionary */
		LinkedHashSet<String> dictionary = ioFile.readResource(Configuration.FILIPINO_DICT);

		/*** Stemmed Filipino words */
		dictionary = stemmer.stemWordList(dictionary);

		/*** Store generated stemmed Filipino words */
		ioFile.writeResource(Configuration.STEMMED_FILE, Configuration.OVERWRITE_FILE, dictionary);

		/*** Stemmed Filipino language model */
		LanguageModel languageModel = new LanguageModel(dictionary, Configuration.nGram);
		dictionary = languageModel.generateNGram();

		/*** Store generated stemmed Filipino language model */
		ioFile.writeResource(Configuration.NGRAM_FILE, Configuration.OVERWRITE_FILE, dictionary);
		dictionary = null;

		/*** Automaton representation of English and Filipino dictionary */
		MDAG engDict = DAWG.dictAutomaton(Configuration.ENGLISH_DICT);
		MDAG filiDict = DAWG.dictAutomaton(Configuration.FILIPINO_DICT);

		/*** ERROR DETECTION: Dictionary look-up and N-gram approach */
		DictLookUp dictLookUp = new DictLookUp(engDict, filiDict);
		NGramStatistics nGramStats = new NGramStatistics(Configuration.NGRAM_FILE);

		/*** ERROR CORRECTION: Merge-split */
		SplitCorrection splitCorrector = new SplitCorrection(filiDict);
		MergeCorrection mergeCorrector = new MergeCorrection(stemmer);

		/*** List of candidate suggestions */
		LinkedHashSet<String> candidateSuggestions = null;

		/*** TESTING: Logger for testing */
		Logger logger = new Logger();
		/*** sentence counter */
		int sentenceNumber = 0;

		/*** TESTING: sentences */
		ArrayList<String> document = new ArrayList<>();

		document.add("Ang mga bata na magaganda.*#");
		document.add("Sila rin ay bata na magaganda.*#");
		document.add("mag laro ay palang na magaganda.*#");
		document.add("Mg-texts laro Magelan ay magagandangbabae na magagnda text subtexts.*#");

		/*** Tokenizer to separate marks with words. */
		Tokenizer tokenizer = new Tokenizer();
		for (String sentence : document) {
			sentenceNumber++;

			/*** Array of words after tokenization. */
			String[] words = tokenizer.tokenize(sentence);

			// words.length-1 = remove the marker '*#'
			for (int wordCounter = 0; wordCounter < words.length - 1; wordCounter++) {

				/*** Current word in the sentence. */
				String cWord = words[wordCounter];
				candidateSuggestions = new LinkedHashSet<>();

				// Skipping the owrd if it is a punctuation mark/ proper noun.
				if (tokenizer.isDelimeters(cWord) || tokenizer.isProperNoun(cWord)) {
					if (Configuration.LOGGER) {
						candidateSuggestions.add("Delimeter / ProperNoun");
						logger.log(sentenceNumber, sentence, cWord, false, candidateSuggestions);
					}
					continue;
				}

				// ---------------------------------------------
				// ---------------------------------------------
				// ERROR DETECTION
				// ---------------------------------------------
				// ---------------------------------------------

				/***
				 * state(true/false) whether the word is present or absent in
				 * the dictionary.
				 */
				boolean inDictionary = dictLookUp.checkDict(cWord);

				// INFLECTED CASE:
				// Stem the word and do a dictionary look-up
				if (!inDictionary) {
					// STEM OF THE FILIPINO WORD
					String word = stemmer.stemming(cWord);
					inDictionary = dictLookUp.checkFiliDict(word);
				}

				// CODE-SWITCHING:
				// Check if the cWord is not in dictionary and contains hyphen
				if (!inDictionary && cWord.contains("-")) {

					/*** prefix and borrowed word */
					String[] codeSwitchWord = cWord.split("-");

					boolean isPrefix = stemmer.isPrefix(codeSwitchWord[0]);
					boolean isEngWord = dictLookUp.checkEngDict(codeSwitchWord[1]);

					// Check if prefix is the only wrong
					if (isEngWord && !isPrefix) {
						if (Configuration.LOGGER) {
							candidateSuggestions.add("Wrong prefix of the word");
							logger.log(sentenceNumber, sentence, cWord, inDictionary, candidateSuggestions);
						}
						continue;
					}

					inDictionary = (isPrefix && isEngWord);
				}

				// N-GRAM:
				// Check the N-gram statistics if cWord do not exists in the
				// language.
				if (!inDictionary && !stemmer.isPrefix(cWord)) {
					inDictionary = nGramStats.hasHighNGramStatistics(cWord);
				}

				// ---------------------------------------------
				// ---------------------------------------------
				// ERROR CORRECTION FOR THE MISPPLED WORD
				// ---------------------------------------------
				// ---------------------------------------------

				/*** Next word in the sentence. */
				String nWord = words[wordCounter + 1];

				if (!inDictionary) {
					/*** Add split correction suggestions */
					candidateSuggestions = splitCorrector.splitSuggestion(cWord);

					/*** Add merge correction suggestions */
					String mergeCorrectionSuggestion = mergeCorrector.setConsecutiveWords(cWord, nWord);
					if (!mergeCorrectionSuggestion.equals("")) {
						wordCounter++;
						candidateSuggestions.add(mergeCorrectionSuggestion);
					}

					// Check if no suggestion produced by split-merge
					// correction.
					if (candidateSuggestions.size() == 0) {
						/*** Add automaton correction suggestions */
						candidateSuggestions.addAll(LevenshteinAutomaton.iterativeFuzzySearch(2, cWord, filiDict));
					}
				}

				if (Configuration.LOGGER)
					logger.log(sentenceNumber, sentence, cWord, inDictionary, candidateSuggestions);
			}
		}

		// FOR TESTING: Log output of test sentence
		ioFile.writeResource(Configuration.LOG_FILE, Configuration.OVERWRITE_FILE, logger.getLog());
	}

}
