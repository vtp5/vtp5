package vtp5.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;
import vtp5.logic.TestFile;

public class VTP5 extends JFrame {
	// TODO Generate the serialVersionUID once class has been finished.

	// GUI components.
	// private JMenu newTestMenu, importFilesMenu, recordsMenu, helpMenu,
	// settingsMenu;
	// private JMenuItem importText;
	// private JMenuBar bar;

	// Components for button panel at top of frame
	private JPanel buttonPanel;
	private JButton importFileButton, leaderboardButton, settingsButton,
			helpButton, aboutButton;

	// Components in the main area of the frame
	private JPanel mainPanel;
	private JLabel promptLabel;
	private JTextField answerField;
	private JButton enterButton;
	private JButton passButton;
	private JList<String> statsList;
	private JScrollPane statsScrollPane;
	private JList<String> guessedAnswersList;
	private JScrollPane guessedAnswersScrollPane;
	private JProgressBar progressBar;

	private JFileChooser chooser = new JFileChooser();

	private FileNameExtensionFilter chooserFilter = new FileNameExtensionFilter(
			"Text Files (*.txt)", "txt");

	TestFile test;

	public VTP5() {
		/*
		 * Sets up JMenuBar. bar = new JMenuBar(); newTestMenu = new
		 * JMenu("New Test"); newTestMenu.setMnemonic(KeyEvent.VK_N);
		 * 
		 * importFilesMenu = new JMenu("Import");
		 * importFilesMenu.setMnemonic(KeyEvent.VK_I); importText = new
		 * JMenuItem("Text File"); importText.addActionListener(new
		 * MenuItemListener()); importFilesMenu.add(importText);
		 * 
		 * recordsMenu = new JMenu("Records");
		 * recordsMenu.setMnemonic(KeyEvent.VK_R);
		 * 
		 * helpMenu = new JMenu("Help"); helpMenu.setMnemonic(KeyEvent.VK_H);
		 * 
		 * settingsMenu = new JMenu("Settings");
		 * settingsMenu.setMnemonic(KeyEvent.VK_S);
		 * 
		 * bar.add(newTestMenu); bar.add(importFilesMenu); bar.add(recordsMenu);
		 * bar.add(helpMenu); bar.add(settingsMenu); setJMenuBar(bar);
		 */

		// Sets up JFileChooser
		chooser.setFileFilter(chooserFilter);

		// Set up button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new MigLayout());

		importFileButton = new JButton("Import Test File");
		leaderboardButton = new JButton("View Leaderboards");
		settingsButton = new JButton("Settings");
		helpButton = new JButton("Help");
		aboutButton = new JButton("About");

		buttonPanel.add(importFileButton, "align left");
		buttonPanel.add(leaderboardButton, "push, align right");
		buttonPanel.add(settingsButton, "align right");
		buttonPanel.add(helpButton, "align right");
		buttonPanel.add(aboutButton, "align right");

		// Set up main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("insets 5", "", ""));

		promptLabel = new JLabel(
				"<html><div style=\"text-align:center;\">Prompt</div></html>");
		answerField = new JTextField("Enter answer here");
		enterButton = new JButton("Enter");
		passButton = new JButton("Pass");

		// Set up JLists and their respective ListModels
		statsList = new JList<>(new String[] { "Stats:", "1", "2", "3", "4" });
		statsList.setVisibleRowCount(5);
		statsScrollPane = new JScrollPane(statsList);
		guessedAnswersList = new JList<>(new String[] {
				"Already guessed answers:", "1", "2", "3", "4" });
		guessedAnswersList.setVisibleRowCount(5);
		guessedAnswersScrollPane = new JScrollPane(guessedAnswersList);

		progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
		progressBar.setValue(50);

		// Add components to main panel
		mainPanel.add(promptLabel, "span 3, push, wrap");
		mainPanel.add(answerField, "span 2 2, grow");
		mainPanel.add(enterButton, "wrap");
		mainPanel.add(passButton, "wrap");
		mainPanel.add(statsScrollPane, "grow");
		mainPanel.add(guessedAnswersScrollPane, "grow, push, span");

		mainPanel.add(progressBar, "dock east");

		// Add panels to JFrame
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		// Sets JFrame properties.
		setSize(700, 600);
		setTitle("VTP5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		// Maximise JFrame
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	// private class MenuItemListener implements ActionListener {
	// @Override
	// public void actionPerformed(ActionEvent ae) {
	// if (ae.getSource() == importText) {
	// // Open JFileChooser and then creates test file
	// int selected = chooser.showOpenDialog(getParent());
	// if (selected == JFileChooser.APPROVE_OPTION) {
	// // Show confirmation dialog if currently in a test.
	// // If so, clear old test.
	// test = new TestFile(chooser.getSelectedFile());
	// }
	// }
	// }
	//
	// }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VTP5();
			}
		});
	}
}