package vtp5.gui;

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

	// TODO Creating JList for leaderboard (WIP)
	private JList<Object> leaderboards = new JList<>();

	public FinishPanel(final VTP5 parent) {
		setLayout(new MigLayout("fillx"));

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

		// statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Answered correctly: ");
		statsListModel.addElement("Answered incorrectly: ");
		statsListModel.addElement("Total number of guesses: ");
		statsList.setVisibleRowCount(4);
		statsList.setForeground(parent.getTColour());// changes text colour
		Object[] stats = test.getStats();
		statsListModel.removeAllElements();
		// statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Answered correctly: "
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

		// TODO Proper exception handling for the two ActionListeners below

		restartTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					parent.setTest(new TestFile(parent.getTest().importedFile));
				} catch (IOException e) {
					e.printStackTrace();
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
							writer = new PrintWriter(filePath,
									"UTF-8");

							for (int i = 0; i < parent.getTest()
									.getIncorrectCards().size(); i++) {
								Card card = parent.getTest()
										.getIncorrectCards().get(i);
								writer.println(card.getLangFrom().get(0));

								for (int j = 0; j < card.getCorrectLangTo()
										.size(); j++) {
									writer.print(card.getCorrectLangTo().get(j));

									if (!(j == card.getCorrectLangTo().size() - 1)) {
										writer.print("/");
									}

									if (j == card.getCorrectLangTo().size() - 1) {
										writer.print("\n");
									}
								}
							}

							JOptionPane
									.showMessageDialog(
											null,
											"Success! Your wrong answers have been saved to the following file:\n\n"
													+ filePath
													+ "\n\nTo do a test with only these questions, click \"Import Test File\",\n click the \"Text File\" button and then select the file you've just saved.",
											"VTP5",
											JOptionPane.INFORMATION_MESSAGE);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} finally {
							writer.close();
						}
					}

				}
			}
		});

		add(completedLabel, "grow");
		add(statsScrollPane, "grow, spany 2, width 35%!, height 25%!, wrap");
		add(showListLabel, "grow, wrap");
		add(new JScrollPane(table), "grow");
		add(new JScrollPane(leaderboards), "grow, wrap, push");
		add(saveTest, "grow, span, split 2");
		add(restartTest, "grow");
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

		switch (columnIndex) {
		case 0:
			value = card.getLangFrom().toString();
			break;
		case 1:
			value = card.getCorrectLangTo().toString();
			break;
		}

		value = value.substring(1, value.length() - 1);
		return value;
	}
}
