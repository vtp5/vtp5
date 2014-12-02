package vtp5.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestFile {
	// ArrayList of "cards" for a particular test
	private ArrayList<Card> cards = new ArrayList<>();
	// Stores the cards the user got wrong the first time
	private ArrayList<Card> incorrectCards = new ArrayList<>();
	// The user's score
	private int score = 0;

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
			String currentLine;
			br = new BufferedReader(new FileReader(file));
			while ((currentLine = br.readLine()) != null) {
				// Create new card containing relevant data and add it to the
				// ArrayList
				// TODO Merge this code with Converter
				// TODO Polish this code (for example, what about the "+ abl."
				// bit after some verbs and prepositions?)
				ArrayList<String> langFrom = new ArrayList<>(
						Arrays.asList(currentLine));
				ArrayList<String> langTo = new ArrayList<>(Arrays.asList(br
						.readLine().split("/")));

				Card card = new Card(langFrom, langTo);
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

	// public int updateScore(String answer, int index) {
	// if (isCorrect(answer, index) == COMPLETELY_CORRECT) {
	// return score += 1;
	// } else {
	// return score;
	// }
	// }

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
			System.out.println("Incorrect");
			return INCORRECT;
		}

		// if
		// (answer.equalsIgnoreCase(getCards().get(index).getLangTo().get(0))) {
		// // User is correct - but are they partially or completely correct?
		// for (int i = 0; i < getCards().size(); i++) {
		// String s = getCards().get(i).getLangTo().get(0);
		// System.out.println(s);
		// }
		//
		// // Work out which ans
		//
		// if () {
		//
		// }
		// return true;
		// } else {
		// return INCORRECT;
		// }
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

}