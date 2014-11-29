package vtp5.logic;

import java.io.Serializable;
import java.util.ArrayList;

// A "card" holds the data for each word and its translation/meaning
public class Card  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Instance variables are String arrays so the different "parts" can be
	// separated
	private ArrayList<String> langFrom;
	private ArrayList<String> langTo;

	public Card() {
		super();
	}

	public Card(ArrayList<String> langFrom, ArrayList<String> langTo) {
		super();
		this.langFrom = langFrom;
		this.langTo = langTo;
	}

	public ArrayList<String> getLangFrom() {
		return langFrom;
	}

	public void setLangFrom(ArrayList<String> langFrom) {
		this.langFrom = langFrom;
	}

	public ArrayList<String> getLangTo() {
		return langTo;
	}

	public void setLangTo(ArrayList<String> langTo) {
		this.langTo = langTo;
	}

}