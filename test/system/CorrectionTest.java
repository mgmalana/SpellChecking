package system;

import java.io.IOException;
import java.util.LinkedHashSet;

import com.BoxOfC.MDAG.MDAG;

import corrector.MergeTest;
import corrector.SplitTest;
import tokenizer.Tokenizer;
import training.DAWG;
import training.Stemmer;
import training.affixes.Infixes;
import training.affixes.PartialReduplication;
import training.affixes.Prefixes;
import training.affixes.Suffixes;
import utlity.Configuration;

public class CorrectionTest {

	private static MDAG filiDict;
	private static LinkedHashSet<String> x;
	private static String y;

	public static void testCorrection() {

		Prefixes prefixList = new Prefixes();
		Suffixes suffixList = new Suffixes();
		Tokenizer tokenizer = new Tokenizer();
		Infixes infixList = null;
		PartialReduplication redup = null;

		Stemmer stemmer;
		int counter = 0;
		if (Configuration.LIGHT_STEMMER)
			stemmer = new Stemmer(prefixList, suffixList);
		else {
			infixList = new Infixes();
			redup = new PartialReduplication();
			stemmer = new Stemmer(prefixList, suffixList, infixList, redup);
		}

		filiDict = DAWG.dictAutomaton(Configuration.FILIPINO_DICT);

		SplitTest splitCorrector = new SplitTest(filiDict);
		MergeTest mergeCorrector = new MergeTest(stemmer);

		//String[] input = tokenizer.tokenize("matanggap matanggap mag lalaro matanggap palang matanggap.*#");
		//String[] input = tokenizer.tokenize("matanggap matanggap matanggap palang matanggap mag lalaro.*#");
		//String[] input = tokenizer.tokenize("matanggap matanggap matanggap palang matanggap mag.*#");
		String[] input = tokenizer.tokenize("matanggap matanggap matanggap palang matanggap pinag asikaso.*#");
		
		for (int counter1 = 0; counter1 < input.length - 1; counter1++) {
System.out.println(counter1);
			if (!filiDict.contains(input[counter1])) {
				x = splitCorrector.splitSuggestion(input[counter1]);
				if (x.size() != 0) {
					System.out.println(input[counter1] + " " + x.size());
					for (String y : x)
						System.out.println(y);
				}
				
				y = mergeCorrector.setConsecutiveWords(input[counter1], input[counter1+1]);
				if(!y.equals("")){
					counter1++;
					System.out.println(y);
				}
			}
			

		}
	}
}
