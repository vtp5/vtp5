package vtp5.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestFile {

	// TODO Find a way to have multiple answers.

	private String[] langFrom;
	private String[] langTo;

	BufferedReader br = null;

	public TestFile(File file) {
		getVocabFromFile(file);
	}

	public void getVocabFromFile(File file) {
		// Something will translate the file to all the possible questions and
		// answers.
		System.out.println(file + " is being read.");

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String[] getLangFrom() {
		return this.langFrom;
	}

	public void setLangFrom(String[] langFrom) {
		this.langFrom = langFrom;
	}

	public String[] getLangTo() {
		return this.langTo;
	}

	public void setLangTo(String[] langTo) {
		this.langTo = langTo;
	}
}