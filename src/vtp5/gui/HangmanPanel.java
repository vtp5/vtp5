package vtp5.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import vtp5.logic.TestFile;

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
	private static final long serialVersionUID = -6004046195606857488L;

	private StringBuilder userGuess = new StringBuilder("");

	private TestFile test;

	HangmanPanel(TestFile test) {
		this.test = test.clone();

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new HangmanKeyListener());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawString(userGuess.toString(), 500, 500);
	}

	private class HangmanKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// Do nothing
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// Do nothing
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			userGuess.append(arg0.getKeyChar());
			repaint();
		}

	}
}
