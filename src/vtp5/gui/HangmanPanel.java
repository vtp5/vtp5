package vtp5.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import vtp5.logic.Card;
import vtp5.logic.TestFile;

import com.alee.laf.scroll.WebScrollPane;

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
class HangmanPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isStart = true;
	private int numberOfWrongGuesses = 0;
	private StringBuilder userGuess = new StringBuilder("");
	private TestFile test;

	private JList<String> guessedAnswersList;
	private DefaultListModel<String> guessedAnswersListModel;
	private WebScrollPane guessedAnswersScrollPane;

	private double scaler = 1.0;
	private VTP5 parent;

	HangmanPanel(TestFile test, VTP5 parent, double scaler) {
		this.test = test.clone();
		// Reorder cards
		Collections.shuffle(this.test.getCards());

		this.parent = parent;

		setLayout(new MigLayout());

		guessedAnswersListModel = new DefaultListModel<>();
		guessedAnswersListModel
				.addElement("<html><u>Already guessed answers:</u></html>");
		guessedAnswersList = new JList<>(guessedAnswersListModel);
		guessedAnswersList.setVisibleRowCount(6);
		guessedAnswersList.setForeground(parent.getSelectedTheme()
				.getTextColour());

		guessedAnswersList.setSelectionModel(new DisabledItemSelectionModel());
		guessedAnswersList.setFocusable(false);
		guessedAnswersList.setEnabled(false);
		guessedAnswersScrollPane = new WebScrollPane(guessedAnswersList);

		this.scaler = scaler;
		parent.getComponentList().add(
				new ComponentWithFontData(guessedAnswersList, 32));
		new CustomFont().setFont(guessedAnswersList, (int) (scaler * 32.0));

		add(guessedAnswersScrollPane, "width 30%!");

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new HangmanKeyListener());
	}

	private void updateGuessedAnswersList(boolean isCorrect, String userAnswer) {
		// Update guessedAnswersList
		guessedAnswersListModel.removeAllElements();

		// Find out whether to use langFrom or langTo
		ArrayList<String> possibleAnswers = test.isLanguageSwitched() ? test
				.getCards().get(0).getLangFrom() : test.getCards().get(0)
				.getLangTo();
		ArrayList<String> correctAnswers = test.isLanguageSwitched() ? test
				.getCards().get(0).getCorrectLangFrom() : test.getCards()
				.get(0).getCorrectLangTo();

		// Change text depending on whether user got the word right or wrong
		guessedAnswersListModel.addElement("<html><u>"
				+ (isCorrect ? "Already guessed answers: ("
						+ correctAnswers.size() + "/"
						+ (correctAnswers.size() + possibleAnswers.size())
						+ ")" : "All answers:") + "</u></html>");

		// Decide what the list should display based on whether user got the
		// word right or wrong
		for (String s : correctAnswers) {
			guessedAnswersListModel.addElement(s);
		}

		if (!isCorrect) {
			for (String s : possibleAnswers) {
				guessedAnswersListModel.addElement("<html><b><i>" + s
						+ "</i></b></html>");
			}

			if (userAnswer != null) {
				guessedAnswersListModel
						.addElement("<html><u>Your answer:</u></html>");
				guessedAnswersListModel.addElement(userAnswer);
			}
		}

		parent.revalidate();
		parent.repaint();
	}

	void setScaler(double scaler) {
		this.scaler = scaler;
	}

	void updateColours() {
		guessedAnswersList.setForeground(parent.getSelectedTheme()
				.getTextColour());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		new CustomFont().setFont(g2, (int) (50.0 * scaler));

		FontMetrics metrics = g2.getFontMetrics();
		int stringWidth;

		if (isStart) {
			String welcomeMessage = "Press any key to begin Hangman!";
			stringWidth = metrics.stringWidth(welcomeMessage);
			g2.drawString(welcomeMessage, getWidth() / 2 - stringWidth / 2,
					getHeight() - (getHeight() / 11 * 2));
		} else {
			switch (userIsCorrect()) {
			case TestFile.HANGMAN_CORRECT_SO_FAR:
				g2.setColor(Color.GREEN);
				break;
			case TestFile.COMPLETELY_CORRECT:
			case TestFile.PARTIALLY_CORRECT:
				g2.setColor(Color.GREEN);

				// Clear user guess
				userGuess = new StringBuilder("");

				try {
					parent.playSound("/sounds/qcorrect.wav");
				} catch (LineUnavailableException
						| UnsupportedAudioFileException | IOException e) {

					parent.processErrorMessage(e, null);
				}

				updateGuessedAnswersList(true, userGuess.toString());
				break;
			case TestFile.INCORRECT:
				g2.setColor(Color.RED);

				// Clear user guess
				userGuess = new StringBuilder("");

				numberOfWrongGuesses++;

				try {
					parent.playSound("/sounds/qincorrect.wav");
				} catch (LineUnavailableException
						| UnsupportedAudioFileException | IOException e) {

					parent.processErrorMessage(e, null);
				}

				updateGuessedAnswersList(false, userGuess.toString());

				// Reorder cards
				Card c = test.getCards().get(0);
				Collections.shuffle(test.getCards());
				// If the first card is still the same, move it to the end of
				// the ArrayList
				if (c == test.getCards().get(0)) {
					test.getCards().remove(c);
					test.getCards().add(c);
				}
				break;
			}

			// Draw the user's answer string
			stringWidth = metrics.stringWidth(userGuess.toString());
			g2.drawString(userGuess.toString(), getWidth() / 2 - stringWidth
					/ 2, getHeight() - (getHeight() / 11));

			g2.setColor(Color.BLACK);

			// Draw prompt
			String prompt = test.getPrompt(0);
			stringWidth = metrics.stringWidth(prompt);
			g2.drawString(prompt, getWidth() / 2 - stringWidth / 2, getHeight()
					- (getHeight() / 11 * 2));
		}

		// Draw the Hangman!
		drawHangman(g2);

		// Draw lives left string
		String livesLeft = (11 - numberOfWrongGuesses)
				+ ((11 - numberOfWrongGuesses) == 1 ? " life" : " lives")
				+ " left";
		g2.drawString(livesLeft, 10, getHeight() / 2);

		g2.scale(scaler, scaler);
	}

	private void drawHangman(Graphics2D g2) {
		if (numberOfWrongGuesses > 0) {
			g2.drawImage(
					new ImageIcon(getClass().getResource(
							"/images/hangman_" + numberOfWrongGuesses + ".png"))
							.getImage(), getWidth() / 8 * 3, 0,
					getWidth() / 64 * 33, getHeight() / 10 * 7, null);
		}
	}

	private int userIsCorrect() {
		return test.isCorrect(userGuess.toString(), 0, false, false, true);
	}

	private class HangmanKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (isStart) {
				isStart = false;
				parent.revalidate();
				parent.repaint();
			} else {
				int code = e.getKeyCode();
				if (code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z) {
					userGuess.append(Character.toLowerCase(e.getKeyChar()));
				}
				parent.revalidate();
				parent.repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// Do nothing
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// Do nothing
		}

	}
}
