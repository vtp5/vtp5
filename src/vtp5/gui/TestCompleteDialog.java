package vtp5.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TestCompleteDialog extends JDialog {	
	private String[] columnNames = {"Word", "Translation"};
	
	public JTable table;
	
	public TestCompleteDialog(int correctGuesses, int totalGuesses, String[][] wrongAnswers, JFrame parent) {
		super(parent, "Test Complete!");
		table = new JTable(new DefaultTableModel(wrongAnswers, columnNames));
	}
}
