package vtp5.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class TestFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// TODO CHANGE serialVersionUID ONCE CLASS IS FINISHED!!!

	// ArrayList of "cards" for a particular test
	private ArrayList<Card> cards = new ArrayList<>();
	// Stores the cards the user got wrong the first time
	private ArrayList<Card> incorrectCards = new ArrayList<>();
	// The user's score
	private int score = 0;

	// Boolean storing whether the prompt is langFrom or langTo - default
	// (false) means prompt is langFrom
	private boolean isLanguageSwitched = false;

	private BufferedReader br = null;

	// Three "enum"-like constants
	public static final int INCORRECT = 0;
	public static final int PARTIALLY_CORRECT = 1;
	public static final int COMPLETELY_CORRECT = 2;

	public TestFile(File file) {
		getVocabFromFile(file);
	}

	public void getVocabFromFile(File file) {
		System.out.println(file + " is being read.");

		try {
			// Reading file.
			String langFromLine;
			String langToLine;
			br = new BufferedReader(new FileReader(file));
			while ((langFromLine = br.readLine()) != null) {
				// Create new card containing relevant data and add it to the
				// ArrayList
				// TODO Merge this code with Converter
				// TODO Polish this code (for example, what about the "+ abl."
				// bit after some verbs and prepositions?)
				ArrayList<String> langFrom = new ArrayList<>(
						Arrays.asList(langFromLine));

				langToLine = br.readLine();
				ArrayList<String> langTo = new ArrayList<>(
						Arrays.asList(langToLine.split("/")));

				Card card = new Card(langFromLine, langToLine, langFrom, langTo);
				cards.add(card);
			}
			// Catch any exceptions.
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

		// Printlns for debugging/helpful console messages
		for (Card c : cards) {
			System.out.println("--langFrom:--");
			for (String s1 : c.getLangFrom()) {
				System.out.println(s1);
			}

			System.out.println("--langTo:--");
			for (String s2 : c.getLangTo()) {
				System.out.println(s2);
			}

			System.out.println();
		}
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public String getPrompt(int index) {
		if (isLanguageSwitched) {
			// Return langTo prompt
			return cards.get(index).getLangToPrompt();
		} else {
			return cards.get(index).getLangFromPrompt();
		}
	}

	public int isCorrect(String answer, int index) {
		answer = answer.replaceAll("[^a-zA-Z0-9]", "");
		System.out.println(answer);

		// Find out if the user is correct, and if so, remove the correctly
		// guessed answer from the ArrayList
		boolean userIsCorrect = false;
		Card card = cards.get(index);

		for (String s : card.getLangTo()) {
			System.out.println("Original correct answer: " + s);
			System.out.println("Correct answer: " + s);

			if (answer.equalsIgnoreCase(s.replaceAll("[^a-zA-Z0-9]", ""))) {
				System.out.println("User is correct");
				userIsCorrect = true;
				// Remove this answer from the ArayList
				card.getLangTo().remove(s);
				// Add answer to ArrayList containing correctly guessed answers
				card.getCorrectLangTo().add(s);
				break;
			}
		}

		if (userIsCorrect) {
			System.out.println("User is correct");
			// See if user has guessed all of the possible answers
			if (card.getLangTo().isEmpty()) {
				System.out.println("Completely correct");
				score++;
				return COMPLETELY_CORRECT;
			} else {
				System.out.println("Partially correct");
				return PARTIALLY_CORRECT;
			}
		} else {
			// Check if user has already entered the answer (if it's correct)
			for (String s : card.getCorrectLangTo()) {

				if (answer.equalsIgnoreCase(s.replaceAll("[^a-zA-Z0-9]", ""))) {
					// If user has already guessed the answer and it's correct,
					// just return PARTIALLY_CORRECT
					return PARTIALLY_CORRECT;
				}
			}

			System.out.println("Incorrect");
			return INCORRECT;
		}
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public ArrayList<Card> getIncorrectCards() {
		return incorrectCards;
	}

	public int getScore() {
		return score;
	}

	public boolean isLanguageSwitched() {
		return isLanguageSwitched;
	}

	public void setLanguageSwitched(boolean isLanguageSwitched) {
		this.isLanguageSwitched = isLanguageSwitched;
	}

}