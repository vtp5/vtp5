package vtp5.gui;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import vtp5.logic.Card;
import vtp5.logic.TestFile;

public class FinishPanel extends JPanel {

	private JLabel completedLabel;
	private JLabel showListLabel;
	private CustomFont cf;
	private JTable table;
	private TestFile test;
	private String completedMessage;
	private JList<Object> statsList;
	private DefaultListModel<Object> statsListModel;
	private JScrollPane statsScrollPane;
	private WrongAnswersTableModel watm;
	private JButton saveTest;
	private JButton restartTest;

	// TODO Creating JList for leaderboard (WIP)
	private JList<Object> leaderboards;
	
	public FinishPanel(VTP5 parent) {
		cf = new CustomFont();
		test = parent.getTest();
		setLayout(new MigLayout("fillx"));
		completedMessage = "You made it! You got "
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

		statsListModel = new DefaultListModel<>();
		//statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Answered correctly: ");
		statsListModel.addElement("Answered incorrectly: ");
		statsListModel.addElement("Total number of guesses: ");
		statsList = new JList<>(statsListModel);
		statsList.setVisibleRowCount(4);
		statsList.setForeground(parent.getTColour());// changes text colour
		statsScrollPane = new JScrollPane(statsList);
		Object[] stats = test.getStats();
		statsListModel.removeAllElements();
		//statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Answered correctly: "
				+ ((int) stats[0] - test.getCards().size()));
		statsListModel.addElement("Answered incorrectly: " + parent.getTest().getIncorrectCards().size());
		statsListModel.addElement("Total number of guesses: " + stats[2]);

		showListLabel = new JLabel(
				"Here's a list of the words you got wrong the first time:");
		completedLabel = new JLabel(completedMessage);

		watm = new WrongAnswersTableModel(parent.getTest().getIncorrectCards());
		table = new JTable(watm);
		table.setEnabled(false);
		
		leaderboards = new JList<Object>();

		saveTest = new JButton("Save Wrong Answers To New Test");
		restartTest = new JButton("Start Test Again");
		
		cf.setFont(completedLabel, 75);
		cf.setFont(showListLabel, 60);
		cf.setFont(statsList, 40);
		cf.setFont(table, 30);
		cf.setFont(table.getTableHeader(), 30);
		cf.setFont(leaderboards, 40);
		cf.setFont(saveTest, 40);
		cf.setFont(restartTest, 40);
		
		table.setRowHeight(table.getFont().getSize() + 10);

		add(completedLabel, "grow");
		add(statsScrollPane, "grow, spany 2, wrap");
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
