package vtp5.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
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

	// More logical instance variables
	private int totalNumberOfCards;
	private int numberOfIncorrectCards;
	private int totalTimesGuessed;
	private double successRate;

	private transient BufferedReader br = null;

	// Three "enum"-like constants
	public static final int INCORRECT = 0;
	public static final int PARTIALLY_CORRECT = 1;
	public static final int COMPLETELY_CORRECT = 2;

	public TestFile(File file) throws IOException {
		getVocabFromFile(file);
		totalNumberOfCards = cards.size();
	}

	public void getVocabFromFile(File file) throws IOException,
			NullPointerException {
		System.out.println(file + " is being read.");

		// try {
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
			ArrayList<String> langTo = new ArrayList<>(Arrays.asList(langToLine
					.split("/")));

			Card card = new Card(langFromLine, langToLine, langFrom, langTo);
			cards.add(card);
		}
		// Catch any exceptions.
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// Close reader.
		if (br != null) {
			br.close();
		}
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }
		// }

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

		// Find out whether to use langFrom or langTo
		ArrayList<String> possibleAnswers = isLanguageSwitched ? card
				.getLangFrom() : card.getLangTo();
		ArrayList<String> correctAnswers = isLanguageSwitched ? card
				.getCorrectLangFrom() : card.getCorrectLangTo();

		for (String s : possibleAnswers) {
			System.out.println("Original correct answer: " + s);
			System.out.println("Correct answer: " + s);

			if (answer.equalsIgnoreCase(s.replaceAll("[^a-zA-Z0-9]", ""))) {
				System.out.println("User is correct");
				userIsCorrect = true;
				// Remove this answer from the ArayList
				possibleAnswers.remove(s);
				// Add answer to ArrayList containing correctly guessed answers
				correctAnswers.add(s);
				break;
			}
		}

		if (userIsCorrect) {
			System.out.println("User is correct");
			// See if user has guessed all of the possible answers
			if (possibleAnswers.isEmpty()) {
				System.out.println("Completely correct");
				score++;

				// If card was previously incorrect, decrement
				// numberOfIncorrectCards
				if (incorrectCards.contains(cards.get(index))) {
					numberOfIncorrectCards--;
				}

				totalTimesGuessed++;

				// removes card once completed
				cards.remove(index);

				// Calculate success rate
				System.out.println("Calculating success rate...");
				System.out.println("totalTimesGuessed == " + totalTimesGuessed);
				System.out.print("(" + totalNumberOfCards + " - "
						+ cards.size() + ") / " + totalTimesGuessed
						+ " * 100.0");
				successRate = (totalTimesGuessed == 0) ? 0.0
						: ((double) (totalNumberOfCards - cards.size()))
								/ (double) totalTimesGuessed * 100.0;
				System.out.println(" == " + successRate);
				return COMPLETELY_CORRECT;
			} else {
				System.out.println("Partially correct");
				return PARTIALLY_CORRECT;
			}
		} else {
			System.out.println("Incorrect");
			// Check if user has already entered the answer (if it's correct)
			for (String s : correctAnswers) {

				if (answer.equalsIgnoreCase(s.replaceAll("[^a-zA-Z0-9]", ""))) {
					// If user has already guessed the answer and it's correct,
					// just return PARTIALLY_CORRECT
					return PARTIALLY_CORRECT;
				}
			}

			// Add card to ArrayList of "incorrect" cards and update
			// numberOfIncorrectCards
			if (!incorrectCards.contains(cards.get(index))) {
				incorrectCards.add(cards.get(index));
				numberOfIncorrectCards++;
			}

			totalTimesGuessed++;

			// Calculate success rate
			successRate = (totalTimesGuessed == 0) ? 0.0
					: ((double) (totalNumberOfCards - cards.size()))
							/ (double) totalTimesGuessed * 100.0;
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

	public Object[] getStats() {
		return new Object[] { totalNumberOfCards, numberOfIncorrectCards,
				totalTimesGuessed, successRate };
	}

	public int getTotalNumberOfCards() {
		return this.totalNumberOfCards;
	}

	public int getTotalTimesGuessed() {
		return this.totalTimesGuessed;
	}

	public double getSuccessRate() {
		return this.successRate;
	}
}