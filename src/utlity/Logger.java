package utlity;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import training.IOFile;

public class Logger {

	IOFile ioFile = new IOFile();
	LinkedHashSet<String> logList = new LinkedHashSet<>();
	int sentenceNumb = 0;
	int wordCount = 0;

	public void log(int sentenceNumb, String cWord, boolean inDictionary) {
		wordCount++;
		if (this.sentenceNumb != sentenceNumb) {
			this.sentenceNumb = sentenceNumb;
			logList.add("\n" + sentenceNumb + "-------S-E-N-T-E-N-C-E-------");
			logList.add("-------S-E-N-T-E-N-C-E-------" + sentenceNumb);
			logList.add(sentenceNumb + ":  word(" + wordCount + "): " + cWord + " " + inDictionary);
		} else
			logList.add(sentenceNumb + ":  word(" + wordCount + "): " + cWord + " " + inDictionary);
	}

	public LinkedHashSet<String> getLog() {
		return logList;
	}
}
