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

	public int updateScore(String answer, int index) {
		if (isCorrect(answer, index)) {
			return score += 1;
		} else {
			return score;
		}
	}

	public boolean isCorrect(String answer, int index) {
		answer = answer.replaceAll("[^a-zA-Z0-9]", "");
		System.out.println(answer);

		if (answer.equalsIgnoreCase(getCards().get(index).getLangTo().get(0))) {
			for (int i = 0; i < getCards().size(); i++) {
				String s = getCards().get(i).getLangTo().get(0);
				System.out.println(s);
			}
			return true;
		} else {
			return false;
		}
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

}