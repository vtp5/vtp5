package vtp5.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
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
	private ArrayList<JComponent> componentList = new ArrayList<>();

	private JFileChooser chooser = new JFileChooser();

	private FileNameExtensionFilter chooserFilter = new FileNameExtensionFilter(
			"Text Files (*.txt)", "txt");

	TestFile test;

	public VTP5() {
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

		componentList.add(importFileButton);
		componentList.add(leaderboardButton);
		componentList.add(settingsButton);
		componentList.add(helpButton);
		componentList.add(aboutButton);

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
		componentList.add(promptLabel);
		enterButton = new JButton("Enter");
		componentList.add(enterButton);
		passButton = new JButton("Pass");
		componentList.add(passButton);
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

		// Maximise JFrame
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Sets JFrame properties.
		setSize(800, 600);
		setTitle("VTP5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		// Add FrameListener to JFrame so we can detect when the frame is
		// resized
		addComponentListener(new FrameListener(this));
		for (Component c : componentList) {
			setFontSize(c, 34);
		}
	}

	// Method that returns a font object with the "default" font family
	private void setFontSize(Component c, int fontSize) {
		c.setFont(new Font("Franklin Gothic Book", Font.PLAIN, fontSize));
	}

	// *********DO NOT TOUCH ANY CODE BELOW THIS!!*********

	// FrameListener's componentResized() method will be thrown when the JFrame
	// is resized, so we can scale the text
	private class FrameListener extends ComponentAdapter {
		private JFrame frame;
		private Dimension originalSize;

		private FrameListener(JFrame frame) {
			this.frame = frame;
			this.originalSize = frame.getSize();
			System.out.println(originalSize);
		}

		@Override
		public void componentResized(ComponentEvent e) {
			// Scale the text when the frame is resized

			// Calculate the scale factor
			Dimension newSize = frame.getSize();

			// Printlns for debugging
			System.out.println("originalSize: " + originalSize);
			System.out.println("newSize: " + newSize);

			double scaler = Math.min(
					newSize.getWidth() / originalSize.getWidth(),
					newSize.getHeight() / originalSize.getHeight());

			// Printlns for debugging
			System.out.println("Scaler: " + scaler);

			for (Component component : componentList) {
				int newFontSize = (int) ((double) component.getFont().getSize() * scaler);

				// Printlns for debugging:
				System.out.println("newFontSize: " + newFontSize);

				setFontSize(component, newFontSize);
				frame.revalidate();
				frame.repaint();
			}
		}
	}

	public static void main(String[] args) {
		// Get all fonts on user's computer
		// GraphicsEnvironment e = GraphicsEnvironment
		// .getLocalGraphicsEnvironment();
		// Font[] fonts = e.getAllFonts(); // Get the fonts
		// for (Font f : fonts) {
		// System.out.println(f.getFontName());
		// }

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VTP5();
			}
		});
	}
}