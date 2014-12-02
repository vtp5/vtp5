package vtp5.gui;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

public class TestCompleteDialog extends JDialog {
	private String[] columnNames = { "Word", "Translation" };
	private JLabel youMadeIt;

	public JTable table;

	public TestCompleteDialog(int correctGuesses, int totalGuesses, String[][] wrongAnswers, VTP5 parent) {
		super(parent, "Test Complete!");
		setLayout(new MigLayout("fillx"));
		youMadeIt = new JLabel("Well done! You made it.");
		youMadeIt.setFont(parent.font.deriveFont(Font.BOLD, 32));
		table = new JTable(new DefaultTableModel(wrongAnswers, columnNames));
		add(youMadeIt, "span 4 3");
		//add(parent.getStatsScrollPane(), "span 3 2");
		setVisible(true);
	}

	public static void main(String[] args) {
		new TestCompleteDialog(1, 1, null, new VTP5());
	}
}
