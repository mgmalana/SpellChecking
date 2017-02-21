package utlity;

import java.util.LinkedHashSet;

import training.IOFile;

public class Logger {

	IOFile ioFile = new IOFile();
	LinkedHashSet<String> logList = new LinkedHashSet<>();
	int sentenceNumb = 0;
	int wordCount = 0;

	public void log(int sentenceNumb, String sentence, String cWord, boolean inDictionary,
			LinkedHashSet<String> candidateSuggestions) {
		wordCount++;
		if (this.sentenceNumb != sentenceNumb) {
			this.sentenceNumb = sentenceNumb;
			logList.add("\n" + sentenceNumb + "-------S-E-N-T-E-N-C-E-------");
			logList.add(sentence);
			logList.add(sentenceNumb + ":  word(" + wordCount + "): " + cWord + " " + inDictionary + " "
					+ formatSuggestion(candidateSuggestions));
		} else
			logList.add(sentenceNumb + ":  word(" + wordCount + "): " + cWord + " " + inDictionary + " "
					+ formatSuggestion(candidateSuggestions));
	}

	private String formatSuggestion(LinkedHashSet<String> candidateSuggestions) {
		
		if(candidateSuggestions.size() == 0)
			return "";
		
		StringBuilder builder = new StringBuilder();

		builder.append("[");
		for (String suggestion : candidateSuggestions)
			builder.append(suggestion + ", ");

		builder.append("]");
		return builder.toString();
	}

	public LinkedHashSet<String> getLog() {
		return logList;
	}
}
