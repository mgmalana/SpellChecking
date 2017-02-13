package main;

import java.util.Set;

import com.BoxOfC.MDAG.MDAG;

import training.DAWG;
import training.IOFile;
import training.LanguageModel;
import training.Stemmer;
import training.affixes.Prefixes;
import training.affixes.Suffixes;

public class Main {

	public static void main(String[] args) {

		Prefixes prefixList = new Prefixes();
		Suffixes suffixList = new Suffixes();
		IOFile ioFile = new IOFile();

		Stemmer stemmer = new Stemmer(prefixList, suffixList);
		// GET FILIPINO DICT
		Set<String> dictionary = ioFile.readResource(Configuration.FILIPINO_DICT);
		// GET THE STEM OF EACH WORD
		dictionary = stemmer.stemWordList(dictionary);
		// STORE GENERATED STEMMED WORD LIST
		ioFile.trainResource(Configuration.STEMMED_FILE, Configuration.OVERWRITE_FILE, dictionary);
		// GET STEMMED LANGUAGE MODEL
		LanguageModel languageModel = new LanguageModel(dictionary, Configuration.nGram);
		dictionary = languageModel.generateNGram();
		// STORE GENERATED N-GRAM LIST
		ioFile.trainResource(Configuration.LM_FILE, Configuration.OVERWRITE_FILE, dictionary);
		dictionary = null;

		// CREATE AUTOMATON REPRESENTATION OF DICTIOANRY
		MDAG engDict = DAWG.dictAutomaton(Configuration.ENGLISH_DICT);
		MDAG filiDict = DAWG.dictAutomaton(Configuration.FILIPINO_DICT);

		// TOKENIZATION OF INPUT SENTENCES

	}

}
