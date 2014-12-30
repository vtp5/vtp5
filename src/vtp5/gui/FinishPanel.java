package vtp5.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import vtp5.logic.Card;
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
public class FinishPanel extends JPanel {

	private JLabel completedLabel = new JLabel();
	private JLabel showListLabel = new JLabel();
	private CustomFont cf;
	private JTable table = new JTable();
	private TestFile test;
	private String completedMessage;
	private DefaultListModel<Object> statsListModel = new DefaultListModel<>();
	private JList<Object> statsList = new JList<>(statsListModel);
	private JScrollPane statsScrollPane = new JScrollPane(statsList);
	private WrongAnswersTableModel watm;
	private JButton saveTest = new JButton();
	private JButton restartTest = new JButton();
	private JFileChooser wrongAnswersTest = new JFileChooser();

	// Do not remove this variable - it makes text-rescaling work!
	private VTP5 parent;

	// TODO Creating JList for leader boards (WIP)
	private JList<Object> leaderboards = new JList<>();

	public FinishPanel(final VTP5 parent) {
		this.parent = parent;

		setLayout(new MigLayout("fillx"));

		saveTest.setBackground(parent.getBcolour());// changes background colour
		saveTest.setForeground(parent.getFcolour());// changes foreground colour
		parent.getButtonList().add(saveTest);

		restartTest.setBackground(parent.getBcolour());// changes background
														// colour
		restartTest.setForeground(parent.getFcolour());// changes foreground
														// colour
		parent.getButtonList().add(restartTest);

		// Aesthetics!
		saveTest.setFocusable(false);
		restartTest.setFocusable(false);

		setTextColour(parent.getTcolour());

		cf = new CustomFont();
		cf.setFont(completedLabel, 75);
		cf.setFont(showListLabel, 40);
		cf.setFont(statsList, 40);
		cf.setFont(table, 30);
		cf.setFont(table.getTableHeader(), 30);
		cf.setFont(leaderboards, 40);
		cf.setFont(saveTest, 40);
		cf.setFont(restartTest, 40);

		// Add components to componentList in VTP5 class so that font rescaling
		// can occur when frame is resized
		ArrayList<ComponentWithFontData> componentList = parent
				.getComponentList();
		componentList.add(new ComponentWithFontData(completedLabel, 75));
		componentList.add(new ComponentWithFontData(showListLabel, 40));
		componentList.add(new ComponentWithFontData(statsList, 40));
		componentList.add(new ComponentWithFontData(table, 30));
		componentList
				.add(new ComponentWithFontData(table.getTableHeader(), 30));
		componentList.add(new ComponentWithFontData(leaderboards, 40));
		componentList.add(new ComponentWithFontData(saveTest, 40));
		componentList.add(new ComponentWithFontData(restartTest, 40));

		// TODO Proper exception handling for the two ActionListeners below

		restartTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					parent.setTest(new TestFile(parent.getTest()
							.getImportedFile()));
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane
							.showMessageDialog(
									null,
									"The following error occurred:\n\n"
											+ e.toString()
											+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
									"VTP5", JOptionPane.ERROR_MESSAGE);
				}
				parent.setUpTest();
			}
		});

		saveTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parent.getTest().getIncorrectCards().size() == 0) {
					JOptionPane
							.showMessageDialog(
									parent,
									"You don't have any wrong answers! Why would you want to save them to a test?",
									"VTP5", JOptionPane.INFORMATION_MESSAGE);
				} else {
					int answer = wrongAnswersTest.showSaveDialog(parent);
					if (answer == JFileChooser.APPROVE_OPTION) {
						String filePath = wrongAnswersTest.getSelectedFile()
								.getAbsolutePath();

						if (!filePath.endsWith(".txt")) {
							filePath = filePath + ".txt";
						}

						PrintWriter writer = null;

						try {
							writer = new PrintWriter(filePath, "UTF-8");

							for (int i = 0; i < parent.getTest()
									.getIncorrectCards().size(); i++) {
								Card card = parent.getTest()
										.getIncorrectCards().get(i);
								writer.println(card.getLangFromPrompt());

								// NOT SURE IF THIS WILL ALWAYS WORK...
								// for (int j = 0; j < card.getCorrectLangTo()
								// .size(); j++) {
								// writer.print(card.getCorrectLangTo().get(j));
								//
								// if (!(j == card.getCorrectLangTo().size() -
								// 1)) {
								// writer.print("/");
								// }
								//
								// if (j == card.getCorrectLangTo().size() - 1)
								// {
								// writer.println();
								// }
								// }

								// ... so I (Ming) changed it to this:
								writer.println(card.getLangToPrompt());
							}

							JOptionPane
									.showMessageDialog(
											null,
											"Success! Your wrong answers have been saved to the following file:\n\n"
													+ filePath
													+ "\n\nTo do a test with only these questions, click \"Import Test File\",\nclick the \"Text File\" button and then select the file you've just saved.",
											"VTP5",
											JOptionPane.INFORMATION_MESSAGE);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							JOptionPane
									.showMessageDialog(
											null,
											"The following error occurred:\n\n"
													+ e.toString()
													+ "\n\nThat's really sad :(. This could be because VTP5 can't find or access the file you selected.\nPlease report the problem if it keeps happening.",
											"VTP5", JOptionPane.ERROR_MESSAGE);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							JOptionPane
									.showMessageDialog(
											null,
											"The following error occurred:\n\n"
													+ e.toString()
													+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
											"VTP5", JOptionPane.ERROR_MESSAGE);
						} finally {
							writer.close();
						}
					}

				}
			}
		});
	}

	// DO NOT DELETE THIS METHOD - it makes text-rescaling work!
	public void updatePanel() {
		removeAll();
		test = parent.getTest();
		completedMessage = "<html>You made it! You got "
				+ new BigDecimal(String.valueOf(test.getSuccessRate()))
						.setScale(1, BigDecimal.ROUND_HALF_UP).toString()
				+ "%.";

		if (test.getSuccessRate() == 100) {
			completedMessage = completedMessage + " That's amazing!";
		} else if (test.getSuccessRate() >= 90) {
			completedMessage = completedMessage + " Well done!";
		} else if (test.getSuccessRate() >= 75) {
			completedMessage = completedMessage + " Not too bad!";
		} else if (test.getSuccessRate() >= 50) {
			completedMessage = completedMessage + " Needs some work!";
		} else {
			completedMessage = completedMessage + " Ouch!";
		}

		completedMessage += "</html>";

		statsList.setVisibleRowCount(4);
		statsList.setForeground(parent.getTcolour());// changes text colour
		Object[] stats = test.getStats();
		statsListModel.removeAllElements();
		statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Total number of words: "
				+ ((int) stats[0] - test.getCards().size()));
		statsListModel.addElement("Answered incorrectly: "
				+ parent.getTest().getIncorrectCards().size());
		statsListModel.addElement("Total number of guesses: " + stats[2]);

		showListLabel
				.setText("<html>Here's a list of the words you got wrong the first time:</html>");
		completedLabel.setText(completedMessage);

		watm = new WrongAnswersTableModel(parent.getTest().getIncorrectCards());
		table.setModel(watm);
		table.setEnabled(false);
		table.setRowHeight(table.getFont().getSize() + 10);

		saveTest.setText("Save Wrong Answers To New Test");
		restartTest.setText("Start Test Again");

		add(completedLabel, "grow");
		add(statsScrollPane, "grow, spany 2, width 35%!, wrap");
		add(showListLabel, "grow, wrap");
		add(new JScrollPane(table), "grow");
		add(new JScrollPane(leaderboards), "grow, wrap, push");
		add(saveTest, "grow, span, split 2");
		add(restartTest, "grow");
	}

	void setTextColour(Color text) {
		completedLabel.setForeground(text);
		statsList.setForeground(text);
		showListLabel.setForeground(text);
		table.setForeground(text);
		table.getTableHeader().setForeground(text);
		leaderboards.setForeground(text);
	}
}

class WrongAnswersTableModel extends AbstractTableModel {

	private ArrayList<Card> wrongAnswers;

	public WrongAnswersTableModel(ArrayList<Card> wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return wrongAnswers.size();
	}

	@Override
	public String getColumnName(int column) {
		String name = "";

		switch (column) {
		case 0:
			name = "Word";
			break;
		case 1:
			name = "Translation";
			break;
		}

		return name;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Card card = wrongAnswers.get(rowIndex);
		String value = "";

		// Ming: I changed the methods so that the string gotten is the original
		// word string
		switch (columnIndex) {
		case 0:
			value = card.getLangFromPrompt();
			break;
		case 1:
			value = card.getLangToPrompt();
			break;
		}

		// value = value.substring(1, value.length() - 1);
		return value;
	}
}
