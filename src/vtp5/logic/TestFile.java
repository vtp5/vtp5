package vtp5.logic;

import java.io.File;

public class TestFile {
	
	// TODO Find a way to have multiple answers.
	
	private String[] langFrom;
	private String[] langTo;
	
	public TestFile(File file) {
		getVocabFromFile(file);
	}
	
	public void getVocabFromFile(File file) {
		// Something will translate the file to all the possible questions and answers.
		// System.out.println(file);
	}
	
	public String[] getLangFrom() {	
		return this.langFrom;	}
	
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
