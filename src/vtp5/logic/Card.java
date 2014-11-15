package vtp5.logic;

// A "card" holds the data for each word and its translation/meaning
public class Card {
	// Instance variables are String arrays so the different "parts" can be
	// separated
	private String[] langFrom;
	private String[] langTo;

	public Card() {
		super();
	}

	public Card(String[] langFrom, String[] langTo) {
		super();
		this.langFrom = langFrom;
		this.langTo = langTo;
	}

	public String[] getLangFrom() {
		return langFrom;
	}

	public void setLangFrom(String[] langFrom) {
		this.langFrom = langFrom;
	}

	public String[] getLangTo() {
		return langTo;
	}

	public void setLangTo(String[] langTo) {
		this.langTo = langTo;
	}

}