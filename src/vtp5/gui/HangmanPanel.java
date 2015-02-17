package vtp5.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
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

	private StringBuilder userGuess = new StringBuilder("");
	private TestFile test;

	private JList<String> guessedAnswersList;
	private DefaultListModel<String> guessedAnswersListModel;
	private WebScrollPane guessedAnswersScrollPane;

	private double scaler = 1.0;
	private VTP5 parent;

	HangmanPanel(TestFile test, VTP5 parent, double scaler) {
		this.test = test.clone();
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

		add(guessedAnswersScrollPane, "grow");

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
		int stringWidth = metrics.stringWidth(userGuess.toString());
		g2.drawString(userGuess.toString(), getWidth() / 2 - stringWidth / 2,
				getHeight() - (getHeight() / 11));

		// Draw the Hangman!
		drawHangman(g2);

		g2.scale(scaler, scaler);

		// if (hangmanGame.getNumOfGuesses() <= 11) {
		// g.setStroke(new BasicStroke(7));
		// g.drawLine(100, 400, 450, 400);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 10) {
		// g.drawLine(100, 400, 100, 130);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 9) {
		// g.drawLine(100, 350, 150, 400);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 8) {
		// g.drawLine(100, 130, 330, 130);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 7) {
		// g.drawLine(100, 180, 150, 130);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 6) {
		// g.drawLine(330, 130, 330, 170);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 5) {
		// g.drawOval(300, 170, 60, 60);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 4) {
		// g.drawLine(330, 230, 330, 320);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 3) {
		// g.drawLine(270, 230, 330, 270);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 2) {
		// g.drawLine(390, 230, 330, 270);
		// }
		// if (hangmanGame.getNumOfGuesses() <= 1) {
		// g.drawLine(270, 370, 330, 320);
		// }
		// if (hangmanGame.getNumOfGuesses() == 0) {
		// g.drawLine(390, 370, 330, 320);
		// g.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND,
		// BasicStroke.JOIN_BEVEL));
		// g.drawLine(320, 190, 320, 190);
		// g.drawLine(340, 190, 340, 190);
		// g.setStroke(new BasicStroke(5));
		// g.drawOval(320, 200, 15, 15);
		// }
	}

	private void drawHangman(Graphics2D g2) {
		g2.setColor(Color.BLACK);

		g2.fillRect(getWidth() / 4, getHeight() - (getHeight() / 11 * 5),
				getWidth() / 5 * 3, getHeight() / 30);
		g2.fillRect(getWidth() / 2, getHeight() - (getHeight() / 11 * 5),
				getWidth() / 50, getHeight() / 24);
		g2.fillRect(getWidth() / 2, getHeight() - (getHeight() / 11 * 5)
				+ (getHeight() / 24), getWidth() / 4 + getWidth() / 5 * 3
				- getWidth() / 2, getHeight() / 48);

		g2.fillRect(getWidth() / 4, 5, getHeight() / 30, getHeight()
				- (getHeight() / 11 * 5) - 5);
		g2.fillRect(getWidth() / 4 + getHeight() / 30 + getWidth() / 200, 5,
				getWidth() / 200, getHeight() - (getHeight() / 11 * 5) - 5);
		g2.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2.drawLine(
				getWidth() / 4 + getHeight() / 30 + getWidth() / 150,
				getHeight() - (getHeight() / 11 * 6),
				getWidth()
						/ 4
						+ getHeight()
						/ 30
						+ getWidth()
						/ 150
						+ ((getHeight() - (getHeight() / 11 * 5)) - (getHeight() - (getHeight() / 11 * 6))),
				getHeight() - (getHeight() / 11 * 5));
		g2.setStroke(new BasicStroke(1));

		g2.fillRect(getWidth() / 4, 5, getWidth() / 20 * 9, getHeight() / 30);
		g2.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		// TODO Improve this horrible, messy code!
		g2.drawLine(
				getWidth() / 4 + getHeight() / 30 + getWidth() / 150,
				5
						+ getHeight()
						/ 30
						+ ((getHeight() - (getHeight() / 11 * 5) + getHeight() / 30) - (getHeight() - (getHeight() / 11 * 6))),
				getWidth()
						/ 4
						+ getHeight()
						/ 30
						+ getWidth()
						/ 150
						+ ((getHeight() - (getHeight() / 11 * 5) + getHeight() / 30) - (getHeight() - (getHeight() / 11 * 6))),
				5 + getHeight() / 30);
		g2.setStroke(new BasicStroke(1));

		g2.fillRect(getWidth() / 4 + getWidth() / 120 * 45, 5,
				getHeight() / 30, getHeight() / 30 * 2);
		g2.fillRect(getWidth() / 4 + getWidth() / 120 * 45 - getWidth() / 240
				* 9, 5 + getHeight() / 30 * 2, getWidth() / 120 * 9
				+ getHeight() / 30, getHeight() / 30 * 3);

		g2.setStroke(new BasicStroke(10));
		g2.drawLine(getWidth() / 4 + getWidth() / 120 * 45,
				5 + getHeight() / 30 * 5, getWidth() / 4 + getWidth() / 120
						* 45 - getWidth() / 120 * 9, 5 + getHeight() / 30 * 10);
		g2.setStroke(new BasicStroke(1));

		g2.setStroke(new BasicStroke(10));
		g2.drawLine(getWidth() / 4 + getWidth() / 120 * 45,
				5 + getHeight() / 30 * 5, getWidth() / 4 + getWidth() / 120
						* 45 + getWidth() / 120 * 9, 5 + getHeight() / 30 * 10);
		g2.setStroke(new BasicStroke(1));

	}

	private class HangmanKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			if (code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z) {
				userGuess.append(Character.toLowerCase(e.getKeyChar()));
			}
			parent.revalidate();
			parent.repaint();
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
