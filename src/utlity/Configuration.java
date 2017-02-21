package utlity;

public class Configuration {

	public final static int MINIMUM_WORD_LENGTH = 3;
	public final static int MAXIMUM_WORD_LENGTH = 5;
	public final static int nGram = 3;
	public final static double nGramThreshold = .40;

	public final static boolean OVERWRITE_FILE = true;
	public final static boolean VERBOSE = false;
	public final static boolean LOGGER = true;
	public final static boolean LIGHT_STEMMER = true;
	
	public final static String LOG_FILE = "log.txt";
	public final static String STEMMED_FILE = "stem.txt";
	public final static String NGRAM_FILE = "languageModel.txt";
	public final static String FILIPINO_DICT = "Filipino.txt";
	public final static String ENGLISH_DICT = "English.txt";
}
