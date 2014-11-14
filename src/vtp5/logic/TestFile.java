package vtp5.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestFile {

	// TODO Find a way to have multiple answers.

	private ArrayList<String> langFrom = new ArrayList<String>();
	private ArrayList<String> langTo = new ArrayList<String>();

	private BufferedReader br = null;
	
	private int switcher = 0;

	public TestFile(File file) {
		getVocabFromFile(file);
	}

	public void getVocabFromFile(File file) {		
		System.out.println(file + " is being read.");

		try {
			// Reading file.
			String currentLine;
			br = new BufferedReader(new FileReader(file));
			while ((currentLine = br.readLine()) != null) {
				// If switcher = 0 (default) add to langFrom.
				// If switcher = 1, add to langTo.
				if (switcher == 0) {
					langFrom.add(currentLine);
					switcher = 1;
				} else if (switcher == 1) {
					langTo.add(currentLine);
					switcher = 0;
				}
			}
			// Catch any excpetions.
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Close reader.
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(langFrom);
		System.out.println(langTo);
	}
	
	public ArrayList<String> getLangFrom() {	
		return this.langFrom;
	}

	
	public void setLangFrom(ArrayList<String> langFrom) {	
		this.langFrom = langFrom;
	}

	
	public ArrayList<String> getLangTo() {	
		return this.langTo;
	}

	
	public void setLangTo(ArrayList<String> langTo) {	
		this.langTo = langTo;
	}
}