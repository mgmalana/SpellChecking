package system;

import java.io.File;
import java.io.IOException;

import com.BoxOfC.MDAG.MDAG;

import detector.DictLookUp;

public class DictionaryLookUpTester {

	public static void dictLookUpTest(String engDict, String filiDict) {

		try {

			MDAG eng = new MDAG(new File(engDict));
			MDAG fili = new MDAG(new File(filiDict));
			DictLookUp lookUp = new DictLookUp(eng, fili);

			String word = "whole";
			lookUp.checkDict(word);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
