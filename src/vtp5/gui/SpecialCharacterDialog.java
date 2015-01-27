package vtp5.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

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
public class SpecialCharacterDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	private JPanel frenchPanel;
	private JButton[] frenchCharacters = { new JButton("é"), new JButton("è"),
			new JButton("à"), new JButton("ù"), new JButton("â"),
			new JButton("ê"), new JButton("î"), new JButton("ô"),
			new JButton("û"), new JButton("ë"), new JButton("ï"),
			new JButton("ç"), new JButton("æ"), new JButton("œ") };

	private JPanel germanPanel;
	private JButton[] germanCharacters = { new JButton("ä"), new JButton("ö"),
			new JButton("ü"), new JButton("ß") };

	private JPanel spanishPanel;
	private JButton[] spanishCharacters = { new JButton("á"), new JButton("é"),
			new JButton("í"), new JButton("ó"), new JButton("ú"),
			new JButton("ü"), new JButton("ñ"), new JButton("¿"),
			new JButton("¡") };

	private JTextField answerField;

	public SpecialCharacterDialog(JTextField answerField) {
		this.answerField = answerField;

		tabbedPane = new JTabbedPane();

		frenchPanel = new JPanel(new MigLayout());
		for (JButton button : frenchCharacters) {
			frenchPanel.add(button);
			button.addActionListener(new EventListener());
		}

		germanPanel = new JPanel(new MigLayout());
		for (JButton button : germanCharacters) {
			germanPanel.add(button);
			button.addActionListener(new EventListener());
		}

		spanishPanel = new JPanel(new MigLayout());
		for (JButton button : spanishCharacters) {
			spanishPanel.add(button);
			button.addActionListener(new EventListener());
		}

		tabbedPane.addTab("French", frenchPanel);
		tabbedPane.addTab("German", germanPanel);
		tabbedPane.addTab("Spanish", spanishPanel);

		setFocusable(false);
		setTitle("Input Special Characters");
		setContentPane(tabbedPane);
		pack();
		setResizable(false);
		setLocationRelativeTo(this);
	}

	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Caret position: "
					+ answerField.getCaretPosition());
			int caretPosition = answerField.getCaretPosition();
			String sub1 = answerField.getText().substring(0, caretPosition);
			String sub2 = answerField.getText().substring(caretPosition);
			String newString = sub1 + ((JButton) arg0.getSource()).getText()
					+ sub2;
			answerField.setText(newString);
			answerField.setCaretPosition(caretPosition + 1);
		}
	}

}
