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
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import vtp5.logic.Card;
import vtp5.logic.TestFile;

import com.alee.laf.list.WebList;
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
	private boolean isEnd = false;
	private boolean hasWon = false;
	private long incorrectMillis = -1L;
	private int numberOfWrongGuesses = 0;
	private StringBuilder userGuess = new StringBuilder("");
	private TestFile test;

	private WebList guessedAnswersList;
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
		guessedAnswersList = new WebList(guessedAnswersListModel);
		guessedAnswersList.setVisibleRowCount(7);
		guessedAnswersList.setForeground(parent.getSelectedTheme()
				.getTextColour());

		guessedAnswersList.setSelectionModel(new DisabledItemSelectionModel());
		guessedAnswersList.setHighlightRolloverCell(false);
		guessedAnswersList.setFocusable(false);
		guessedAnswersScrollPane = new WebScrollPane(guessedAnswersList);

		this.scaler = scaler;
		parent.getComponentList().add(
				new ComponentWithFontData(guessedAnswersList, 24));
		new CustomFont().setFont(guessedAnswersList, (int) (scaler * 24.0));

		add(guessedAnswersScrollPane, "width 22%!");

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new HangmanKeyListener());
	}

	private void updateGuessedAnswersList(boolean isCorrect, String userAnswer) {
		if (!test.getCards().isEmpty()) {
			// Update guessedAnswersList
			guessedAnswersListModel.removeAllElements();
			// Find out whether to use langFrom or langTo
			ArrayList<String> possibleAnswers = test.isLanguageSwitched() ? test
					.getCards().get(0).getLangFrom()
					: test.getCards().get(0).getLangTo();
			ArrayList<String> correctAnswers = test.isLanguageSwitched() ? test
					.getCards().get(0).getCorrectLangFrom() : test.getCards()
					.get(0).getCorrectLangTo();
			if (!isCorrect) {
				guessedAnswersListModel.addElement("<html><b>"
						+ (test.isLanguageSwitched() ? test.getCards().get(0)
								.getLangToPrompt() : test.getCards().get(0)
								.getLangFromPrompt()) + ":</b></html>");
			}
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
				incorrectMillis = -1L;

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
				incorrectMillis = System.currentTimeMillis();

				g2.setColor(Color.RED);

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

				// Clear user guess
				userGuess = new StringBuilder("");
				break;
			}

			// Draw the user's answer string
			stringWidth = metrics.stringWidth(userGuess.toString());
			g2.drawString(userGuess.toString(), getWidth() / 2 - stringWidth
					/ 2, getHeight() - (getHeight() / 11));

			g2.setColor(Color.BLACK);

			// Decide if the game has finished
			if ((11 - numberOfWrongGuesses) == 0) {
				isEnd = true;
				hasWon = false;

				// Draw loss string
				g2.drawString("You lose!", 10, getHeight() / 2);
				g2.drawString("Better luck next time?", 10, getHeight() / 2
						+ metrics.getHeight() + 5);
			} else if (test.getCards().size() == 0) {
				isEnd = true;
				hasWon = true;

				// Draw win string
				g2.drawString("You win!", 10, getHeight() / 2);
				g2.drawString("Well done!", 10,
						getHeight() / 2 + metrics.getHeight() + 5);
			} else {
				// Draw prompt
				String prompt = test.getPrompt(0);
				stringWidth = metrics.stringWidth(prompt);
				g2.drawString(prompt, getWidth() / 2 - stringWidth / 2,
						getHeight() - (getHeight() / 11 * 2));
			}
		}

		// Draw the Hangman!
		drawHangman(g2);

		if (!isEnd) {
			// Draw lives left string
			String livesLeft = (11 - numberOfWrongGuesses)
					+ ((11 - numberOfWrongGuesses) == 1 ? " life" : " lives")
					+ " left";
			g2.drawString(livesLeft, 10, getHeight() / 2);

			// Draw cards left string
			String cardsLeft = test.getCards().size()
					+ (test.getCards().size() == 1 ? " word" : " words")
					+ " left";
			g2.drawString(cardsLeft, 10, getHeight() / 2 + metrics.getHeight()
					+ 5);
		}

		g2.scale(scaler, scaler);
	}

	private void drawHangman(Graphics2D g2) {
		if (hasWon) {
			g2.drawImage(
					new ImageIcon(getClass().getResource(
							"/images/hangman_win.png")).getImage(),
					getWidth() / 8 * 3, 0, getWidth() / 64 * 33,
					getHeight() / 10 * 7, null);
		} else if (numberOfWrongGuesses > 0) {
			g2.drawImage(
					new ImageIcon(getClass().getResource(
							"/images/hangman_" + numberOfWrongGuesses + ".png"))
							.getImage(), getWidth() / 8 * 3, 0,
					getWidth() / 64 * 33, getHeight() / 10 * 7, null);
		}
	}

	private int userIsCorrect() {
		try {
			int result = test.isCorrect(userGuess.toString(), 0, false, false,
					true);
			return result;
		} catch (IndexOutOfBoundsException e) {
			return TestFile.HANGMAN_CORRECT_SO_FAR;
		}
	}

	private class HangmanKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (isStart) {
				isStart = false;
				updateGuessedAnswersList(true, null);
				parent.revalidate();
				parent.repaint();
			} else if (!isEnd) {
				if (incorrectMillis < 0L
						|| (System.currentTimeMillis() - incorrectMillis) > 1000L) {
					int code = e.getKeyCode();
					if ((code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z)
							|| (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9)) {
						userGuess.append(Character.toLowerCase(e.getKeyChar()));
					}
					parent.revalidate();
					parent.repaint();
				}
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
