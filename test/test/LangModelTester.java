package test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import main.Configuration;
import training.IOFile;
import training.LanguageModel;

public class LangModelTester {

	public static void testGenerateLangModel() {

		IOFile ioFile = new IOFile();

		String fileName = Configuration.STEMMED_FILE;

		if (ioFile.isFileExists(ioFile.getResource() + fileName)) {
			LanguageModel model = new LanguageModel(fileName, Configuration.nGram);
			Set<String> nGramFreq = model.generateNGram();

			System.out.println("------");
			System.out.println("Test 1: Generate LM");
			System.out.println("------");

			/**
			 * STORE GENERATED N-GRAM LIST
			 */
			ioFile.trainResource(Configuration.LM_FILE, Configuration.OVERWRITE_FILE, nGramFreq);
			String lastModified = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss")
					.format(new Date(new File(ioFile.getResource() + Configuration.STEMMED_FILE).lastModified()));

			System.out.println("Test 2: Store LM");
			System.out.println(lastModified);
			System.out.println("------");

		} else
			System.out.println("NO STEMMED FILE");

	}

}
