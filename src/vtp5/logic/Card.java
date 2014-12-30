package vtp5.logic;

import java.io.Serializable;
import java.util.ArrayList;

/*VTP5 Copyright (C) 2015  Abdel-Rahim Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
// A "card" holds the data for each word and its translation/meaning
public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Instance variables storing the prompt text for langFrom and langTo
	private String langFromPrompt;
	private String langToPrompt;

	// Instance variables are String arrays so the different "parts" can be
	// separated
	private ArrayList<String> langFrom;
	private ArrayList<String> langTo;

	// Stores parts of the langFrom and langTo that the user has guessed
	// correctly
	private ArrayList<String> correctLangFrom;
	private ArrayList<String> correctLangTo;

	public Card() {
		super();
	}

	public Card(String langFromPrompt, String langToPrompt,
			ArrayList<String> langFrom, ArrayList<String> langTo) {
		super();
		this.langFromPrompt = langFromPrompt;
		this.langToPrompt = langToPrompt;
		this.langFrom = langFrom;
		this.langTo = langTo;
		this.correctLangFrom = new ArrayList<>();
		this.correctLangTo = new ArrayList<>();
	}

	public String getLangFromPrompt() {
		return langFromPrompt;
	}

	public void setLangFromPrompt(String langFromPrompt) {
		this.langFromPrompt = langFromPrompt;
	}

	public String getLangToPrompt() {
		return langToPrompt;
	}

	public void setLangToPrompt(String langToPrompt) {
		this.langToPrompt = langToPrompt;
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

	public ArrayList<String> getCorrectLangFrom() {
		return correctLangFrom;
	}

	public ArrayList<String> getCorrectLangTo() {
		return correctLangTo;
	}

}