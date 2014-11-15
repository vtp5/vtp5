package vtp5.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestFile {
	// ArrayList of "cards" for a particular test
	private ArrayList<Card> cards = new ArrayList<>();
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
				// TODO Can we delete switcher?
				// If switcher = 0 (default) add to langFrom.
				// If switcher = 1, add to langTo.

				// Create new card containing relevant data and add it to the
				// ArrayList
				// TODO Put each "part" in its own string
				String[] langFrom = { currentLine };
				String[] langTo = { br.readLine() };

				Card card = new Card(langFrom, langTo);

				// if (switcher == 0) {
				// card.add(currentLine);
				// switcher = 1;
				// } else if (switcher == 1) {
				// langTo.add(currentLine);
				// switcher = 0;
				// }
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
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

}