package vtp5.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;

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

	private WebPanel frenchPanel;
	private WebButton[] frenchCharacters = { new WebButton("é"), new WebButton("è"),
			new WebButton("à"), new WebButton("ù"), new WebButton("â"),
			new WebButton("ê"), new WebButton("î"), new WebButton("ô"),
			new WebButton("û"), new WebButton("ë"), new WebButton("ï"),
			new WebButton("ç"), new WebButton("æ"), new WebButton("œ") };

	private WebPanel germanPanel;
	private WebButton[] germanCharacters = { new WebButton("ä"), new WebButton("ö"),
			new WebButton("ü"), new WebButton("ß") };

	private WebPanel spanishPanel;
	private WebButton[] spanishCharacters = { new WebButton("á"), new WebButton("é"),
			new WebButton("í"), new WebButton("ó"), new WebButton("ú"),
			new WebButton("ü"), new WebButton("ñ"), new WebButton("¿"),
			new WebButton("¡") };

	private JTextField answerField;

	public SpecialCharacterDialog(JTextField answerField) {
		this.answerField = answerField;

		tabbedPane = new JTabbedPane();

		frenchPanel = new WebPanel(new MigLayout());
		for (WebButton button : frenchCharacters) {
			frenchPanel.add(button);
			button.addActionListener(new EventListener());
		}

		germanPanel = new WebPanel(new MigLayout());
		for (WebButton button : germanCharacters) {
			germanPanel.add(button);
			button.addActionListener(new EventListener());
		}

		spanishPanel = new WebPanel(new MigLayout());
		for (WebButton button : spanishCharacters) {
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
			String newString = sub1 + ((WebButton) arg0.getSource()).getText()
					+ sub2;
			answerField.setText(newString);
			answerField.setCaretPosition(caretPosition + 1);
		}
	}

}
