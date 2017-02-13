package training;

import java.io.File;
import java.io.IOException;

import com.BoxOfC.MDAG.MDAG;

public class DAWG {

	static IOFile ioFile = new IOFile();
	static MDAG mdag = null;

	public static MDAG dictAutomaton(String fileName) {
		String file = ioFile.getResource() + fileName;
		try {
			mdag = new MDAG(new File(file));
			mdag.simplify();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mdag;
	}

}
