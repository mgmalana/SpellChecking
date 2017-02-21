package system;

import java.util.LinkedHashSet;
import java.util.LinkedList;

import com.BoxOfC.LevenshteinAutomaton.LevenshteinAutomaton;
import com.BoxOfC.MDAG.MDAG;

import training.DAWG;
import utlity.Configuration;

public class LevAutomaton {

	public static void testAutomaton() {
		LinkedHashSet<String> ldNeighborsLinkedList = new LinkedHashSet<>();
		MDAG filiDict = DAWG.dictAutomaton(Configuration.FILIPINO_DICT);
		ldNeighborsLinkedList.addAll(LevenshteinAutomaton.iterativeFuzzySearch(2, "bilis", filiDict));

		for (String word : ldNeighborsLinkedList)
			System.out.println(word);
	}
}
