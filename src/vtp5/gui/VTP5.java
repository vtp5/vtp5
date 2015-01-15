package vtp5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import vtp5.Main;
import vtp5.logic.Card;
import vtp5.logic.SpellCheck;
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
public class VTP5 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// TODO Generate the serialVersionUID once class has been finished.

	private FramePanel framePanel;

	// Components for button panel at top of frame
	private JPanel buttonPanel;
	private JButton importFileButton, settingsButton, helpButton, aboutButton,
			saveButton, startAgainButton;
	private int questionIndex = 0;

	// Components in the main area of the frame
	private JPanel mainPanel;
	private JCheckBox switchLanguageCheck;
	private JLabel promptLabel;
	private JTextField answerField;
	private JButton characterButton;
	private JButton enterButton;
	private JButton passButton;

	private JList<String> statsList;
	private JScrollPane statsScrollPane;
	private DefaultListModel<String> statsListModel;
	private JList<String> guessedAnswersList;
	private JScrollPane guessedAnswersScrollPane;
	private DefaultListModel<String> guessedAnswersListModel;

	private JProgressBar progressBar;
	private JSeparator separator;

	private ArrayList<ComponentWithFontData> componentList = new ArrayList<>();

	private JFileChooser txtChooser = new JFileChooser();
	private JFileChooser csvChooser = new JFileChooser();
	private JFileChooser progressOpenChooser = new JFileChooser();
	private JFileChooser progressSaveChooser = new JFileChooser();

	// Settings file
	Properties properties;
	OutputStream output;

	// Special character dialog
	SpecialCharacterDialog characterDialog;

	// Number of questions dialog
	QuestionsDialog questionsDialog;

	// Components for Settings Dialog
	private JDialog settingsDialog;
	private JButton changeButtonColour, changePromptColour, changeTextColour,
			checkForUpdateButton, changeBackgroundColour;
	private JCheckBox experimentalCheck, changingFrameColourCheck;
	private HyperlinkLabel exInfoLabel;

	// Components for About Dialog
	private AboutDialog abtDialog;

	private ImageIcon logo = new ImageIcon(getClass().getResource(
			"/images/vtpsmall.png"));
	private ArrayList<JButton> buttonList = new ArrayList<>();

	// finishPanel instance variable - must create the object HERE (i.e. as soon
	// as program begins), otherwise text-rescaling won't work properly
	private FinishPanel finishPanel;

	// The all-import TestFile object!
	private TestFile test;
	// Timer to help with "experimental features"
	private Timer experimentalTimer = new Timer(750, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			updatePrompt(questionIndex);
			experimentalTimer.stop();
		}
	});

	private JColorChooser colourChooser = new JColorChooser();
	private Color buttonColour = Color.BLACK;
	private Color fcolour = Color.WHITE;
	private Color textColour = Color.BLACK;
	private Color panelColour = Color.WHITE;

	public Font font;

	private int alpha = 191;
	private int minColour = 70;

	public VTP5() {
		// Load spell-checker
		SpellCheck.loadSpellChecker();

		// Sets up JFileChooser
		txtChooser.setFileFilter(new FileNameExtensionFilter(
				"Text Files (*.txt)", "txt"));
		csvChooser.setFileFilter(new FileNameExtensionFilter(
				"CSV Files (*.csv)", "csv"));
		progressOpenChooser.setFileFilter(new FileNameExtensionFilter(
				"VTP5 Progress Files (*.vtp5)", "vtp5"));

		txtChooser.setMultiSelectionEnabled(true);
		csvChooser.setMultiSelectionEnabled(true);

		framePanel = new FramePanel();// make primary panel
		framePanel.setLayout(new BorderLayout());// set layout

		// Set up button panel
		buttonPanel = new JPanel();// make panel for buttons
		buttonPanel.setLayout(new MigLayout());// set layout

		importFileButton = new JButton("Import Test File");// creates buttons
		importFileButton.setBackground(buttonColour);// changes background
														// colour
		importFileButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(importFileButton);

		saveButton = new JButton("Complete Later");
		saveButton.setBackground(buttonColour);
		saveButton.setForeground(fcolour);
		saveButton.setEnabled(false);
		buttonList.add(saveButton);

		startAgainButton = new JButton("Start Again");
		startAgainButton.setBackground(buttonColour);
		startAgainButton.setForeground(fcolour);
		startAgainButton.setEnabled(false);
		buttonList.add(startAgainButton);

		// leaderboardButton = new JButton("View Leaderboards");// creates
		// buttons
		// leaderboardButton.setBackground(bcolour);// changes background colour
		// leaderboardButton.setForeground(fcolour);// changes foreground colour
		// leaderboardButton.setEnabled(false);
		// buttonList.add(leaderboardButton);

		settingsButton = new JButton("Settings");// creates buttons
		settingsButton.setBackground(buttonColour);// changes background colour
		settingsButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(settingsButton);

		helpButton = new JButton("Help");// ads button
		helpButton.setBackground(buttonColour);// changes background colour
		helpButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(helpButton);

		aboutButton = new JButton("About");// creates buttons
		aboutButton.setBackground(buttonColour);// changes background colour
		aboutButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(aboutButton);

		// Sets up about dialog
		abtDialog = new AboutDialog();

		changeButtonColour = new JButton("Change Button Colour");
		changePromptColour = new JButton("Change Prompt Colour");
		changeTextColour = new JButton("Change Button Text Colour");
		changeBackgroundColour = new JButton("Change Background Colour");
		changeBackgroundColour.setEnabled(false);
		checkForUpdateButton = new JButton("Check For Updates");
		experimentalCheck = new JCheckBox("Enable Experimental Features");
		changingFrameColourCheck = new JCheckBox(
				"Enable Dynamic Background Colour", true);
		exInfoLabel = new HyperlinkLabel(
				"<html>Click here for more information<br />on experimental features</html>",
				"https://github.com/duckifyz/VTP5/wiki/Help#experimental-features");

		settingsDialog = new JDialog(this, "Settings");
		settingsDialog.setLayout(new MigLayout("fillx", "", "[][][]10[]10[]"));
		settingsDialog.add(checkForUpdateButton, "alignx center, wrap");
		settingsDialog.add(new JSeparator(), "grow, wrap");
		settingsDialog.add(changingFrameColourCheck, "alignx center, wrap");
		settingsDialog.add(changeButtonColour, "alignx center, wrap");
		settingsDialog.add(changePromptColour, "alignx center, wrap");
		settingsDialog.add(changeTextColour, "alignx center, wrap");
		settingsDialog.add(changeBackgroundColour, "alignx center, wrap");
		settingsDialog.add(new JSeparator(), "grow, wrap");
		settingsDialog.add(experimentalCheck, "alignx center, wrap");
		settingsDialog.add(exInfoLabel);
		settingsDialog.pack();
		settingsDialog.setResizable(false);
		settingsDialog.setLocationRelativeTo(this);

		separator = new JSeparator();
		separator.setBackground(buttonColour);

		// JProgressBar setup
		progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 1000);
		progressBar.setValue(0);
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		progressBar.setString("");

		componentList.add(new ComponentWithFontData(importFileButton, 34));// adds
		componentList.add(new ComponentWithFontData(saveButton, 34)); // to
		// list
		// componentList.add(new ComponentWithFontData(leaderboardButton,
		// 34));// adds
		// to
		// list
		componentList.add(new ComponentWithFontData(settingsButton, 34));// adds
																			// to
																			// list
		componentList.add(new ComponentWithFontData(helpButton, 34));// adds to
																		// list
		componentList.add(new ComponentWithFontData(aboutButton, 34));// adds to
																		// list
		componentList.add(new ComponentWithFontData(startAgainButton, 34));

		componentList.add(new ComponentWithFontData(progressBar, 24));

		// Prevent the buttons from being focusable so there is no ugly
		// rectangle when you click it - this is purely for aesthetic reasons
		importFileButton.setFocusable(false);
		saveButton.setFocusable(false);
		// leaderboardButton.setFocusable(false);
		settingsButton.setFocusable(false);
		helpButton.setFocusable(false);
		aboutButton.setFocusable(false);
		startAgainButton.setFocusable(false);

		EventListener eventListener = new EventListener();
		importFileButton.addActionListener(eventListener);
		saveButton.addActionListener(eventListener);
		aboutButton.addActionListener(eventListener);
		settingsButton.addActionListener(eventListener);
		helpButton.addActionListener(eventListener);
		startAgainButton.addActionListener(eventListener);
		changePromptColour.addActionListener(eventListener);
		changeButtonColour.addActionListener(eventListener);
		changeTextColour.addActionListener(eventListener);
		changeBackgroundColour.addActionListener(eventListener);
		checkForUpdateButton.addActionListener(eventListener);
		changingFrameColourCheck.addActionListener(eventListener);

		buttonPanel.add(importFileButton, "align left");// adds to panel
		buttonPanel.add(startAgainButton, "align left");
		buttonPanel.add(saveButton, "align right");
		// buttonPanel.add(leaderboardButton, "align right");// adds to panel
		buttonPanel.add(settingsButton, "align right");// adds to panel
		buttonPanel.add(helpButton, "align right");// adds to panel
		buttonPanel.add(aboutButton, "align right, wrap");// adds to panel
		buttonPanel.add(separator, "span, grow, push");

		// Set up main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("insets 5", "", "[][][][]5%[]"));

		switchLanguageCheck = new JCheckBox("Switch Language");
		switchLanguageCheck.setFocusable(false);
		switchLanguageCheck.setForeground(textColour);
		switchLanguageCheck.setBackground(Color.GRAY);
		switchLanguageCheck.setEnabled(false);
		switchLanguageCheck.addItemListener(new SwitchLanguageListener());
		componentList.add(new ComponentWithFontData(switchLanguageCheck, 30));

		promptLabel = new JLabel(
				"<html>Click 'Import Test File' to begin.</html>");// creates
		// label
		promptLabel.setForeground(textColour);// changes text colour

		answerField = new JTextField();// creates text field
		answerField.addActionListener(new EventListener());
		answerField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("released ENTER"), "Enter");
		answerField.getActionMap().put("Enter", new ActionEnter());
		answerField.setForeground(textColour);// changes text colour

		componentList.add(new ComponentWithFontData(promptLabel, 72));// adds to
																		// list
		componentList.add(new ComponentWithFontData(answerField, 50));// adds to
		// list

		// Set up character dialog
		characterDialog = new SpecialCharacterDialog(answerField);

		characterButton = new JButton("é");
		characterButton.addActionListener(new EventListener());
		buttonList.add(characterButton);

		characterButton.setBackground(buttonColour);// changes background colour
		characterButton.setForeground(fcolour);// changes foreground colour
		characterButton.setEnabled(false);
		componentList.add(new ComponentWithFontData(characterButton, 32));

		enterButton = new JButton("Enter");// creates buttons
		enterButton.addActionListener(new EventListener());
		buttonList.add(enterButton);

		enterButton.setBackground(buttonColour);// changes background colour
		enterButton.setForeground(fcolour);// changes foreground colour
		enterButton.setEnabled(false);

		componentList.add(new ComponentWithFontData(enterButton, 32));// adds to
																		// list

		passButton = new JButton("Skip");// creates buttons
		passButton.setBackground(buttonColour);// changes background colour
		passButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(passButton);
		passButton.addActionListener(new EventListener());
		passButton.setEnabled(false);

		componentList.add(new ComponentWithFontData(passButton, 32));// adds to
																		// list

		// Prevent the buttons from being focusable so there is no ugly
		// rectangle when you click it - this is purely for aesthetic reasons
		enterButton.setFocusable(false);
		passButton.setFocusable(false);

		DisabledItemSelectionModel selectionModel = new DisabledItemSelectionModel();

		// Set up JLists and their respective ListModels
		statsListModel = new DefaultListModel<>();
		statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Number of words left: ");
		statsListModel.addElement("Total number of guesses: ");
		statsListModel.addElement("Success rate: ");
		statsList = new JList<>(statsListModel);
		statsList.setVisibleRowCount(6);
		statsList.setForeground(textColour);// changes text colour
		statsList.setSelectionModel(selectionModel);
		statsScrollPane = new JScrollPane(statsList);

		guessedAnswersListModel = new DefaultListModel<>();
		guessedAnswersListModel
				.addElement("<html><u>Already guessed answers:</u></html>");
		guessedAnswersList = new JList<>(guessedAnswersListModel);
		guessedAnswersList.setVisibleRowCount(6);
		guessedAnswersList.setForeground(textColour);// changes text colour
		guessedAnswersList.setSelectionModel(selectionModel);
		guessedAnswersScrollPane = new JScrollPane(guessedAnswersList);

		componentList.add(new ComponentWithFontData(statsList, 32));
		componentList.add(new ComponentWithFontData(guessedAnswersList, 32));

		// Set the font size of the text in the components
		for (ComponentWithFontData c : componentList) {
			Component component = c.getComponent();
			setFontSize(component, c.getOriginalFontSize());
		}
		
		for (JButton button : buttonList) {
			
		}

		// Add components to main panel
		mainPanel.add(promptLabel, "span 4, push, wrap, height 30%!");
		mainPanel.add(switchLanguageCheck, "wrap");
		mainPanel.add(answerField, "span 2 2, grow");
		mainPanel.add(characterButton, "span 1 2, growy");
		mainPanel.add(enterButton, "width 250!, wrap");
		mainPanel.add(passButton, "width 250!, wrap");
		mainPanel.add(statsScrollPane, "grow, width 35%!");
		mainPanel.add(guessedAnswersScrollPane,
				"width 60%!, grow, push, span 3");
		mainPanel.add(progressBar, "dock east, width 50!");

		// Add panels to JFrame
		framePanel.add(buttonPanel, BorderLayout.NORTH);
		framePanel.add(mainPanel, BorderLayout.CENTER);
		setContentPane(framePanel);

		// Maximise JFrame
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Sets JFrame properties.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println(screenSize);
		setSize(screenSize.width < 1000 ? screenSize.width : 1000,
				screenSize.height < 650 ? screenSize.height : 650);
		setTitle("VTP5 - " + Main.appVersion);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setIconImage(logo.getImage());

		// Add FrameListener to JFrame so we can detect when the frame is
		// resized
		addComponentListener(new FrameListener(this));

		finishPanel = new FinishPanel(this);
	}

	public void setTest(TestFile test) {
		this.test = test;
	}

	private void setColour(Color background, Color foreground, Color text) {
		for (JButton b : buttonList) {
			b.setForeground(foreground);
			b.setBackground(background);
		}
		promptLabel.setForeground(text);
		statsList.setForeground(text);
		guessedAnswersList.setForeground(text);

		finishPanel.setTextColour(text);

	}

	public static void playSound(String path) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream aIS = AudioSystem.getAudioInputStream(Main.class
					.getResourceAsStream(path));
			clip.open(aIS);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred:\n\n"
									+ e.toString()
									+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updatePrompt(int index) {
		promptLabel.setText("<html>" + test.getPrompt(index) + "</html>");
		updateGuessedAnswersList(true, null);
	}

	// Method that returns a font object with the "default" font family
	private void setFontSize(Component c, int fontSize) {
		CustomFont cf = new CustomFont();
		cf.setFont(c, fontSize);
	}

	private void showChooserDialog(int fileType) {
		try {
			if (fileType == 0) {
				int selected = txtChooser.showOpenDialog(getParent());
				if (selected == JFileChooser.APPROVE_OPTION) {
					File[] files = txtChooser.getSelectedFiles();
					test = new TestFile(files);
				}
			} else if (fileType == 1) {
				JOptionPane
						.showMessageDialog(
								null,
								"Did you expect every feature to be complete in the first alpha version? :P (We're working on it...)",
								"VTP5", JOptionPane.INFORMATION_MESSAGE);
				// int selected = csvChooser.showOpenDialog(getParent());
				// if (selected == JFileChooser.APPROVE_OPTION) {
				// test = new TestFile(csvChooser.getSelectedFile());
				// }
			} else if (fileType == 2) {
				int selected = progressOpenChooser.showOpenDialog(getParent());
				if (selected == JFileChooser.APPROVE_OPTION) {
					File progressFile = progressOpenChooser.getSelectedFile();
					try (ObjectInputStream input = new ObjectInputStream(
							new FileInputStream(progressFile))) {
						test = (TestFile) input.readObject();
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
						JOptionPane
								.showMessageDialog(
										null,
										"The following error occurred:\n\n"
												+ e.toString()
												+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
										"VTP5", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred:\n\n"
									+ e.toString()
									+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred:\n\n"
									+ e.toString()
									+ "\n\nIt's likely that the file you're trying to import isn't formatted correctly.\nPlease check the file and try again.\nIf the problem persists, please report it.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void displayColorChooser(int index) {
		colourChooser.setPreviewPanel(new JPanel());
		JDialog d = JColorChooser.createDialog(null, "", true, colourChooser,
				null, null);
		Color c;
		switch (index) {
		case 1:
			d.setVisible(true);
			c = colourChooser.getColor();
			if (c != null) {
				setColour(c, fcolour, textColour);
				buttonColour = c;
				c = null;
			}
			break;
		case 2:
			d.setVisible(true);
			c = colourChooser.getColor();
			if (c != null) {
				setColour(buttonColour, fcolour, c);
				textColour = c;
				c = null;
			}
			break;
		case 3:
			d.setVisible(true);
			c = colourChooser.getColor();
			if (c != null) {
				setColour(buttonColour, c, textColour);
				fcolour = c;
				c = null;
			}
			break;

		case 4:
			d.setVisible(true);
			c = colourChooser.getColor();
			if (c != null) {
				updateFrameColour(c);
				c = null;
			}
			break;

		}
	}

	private void doLogic() {
		if (enterButton.getText().equals("Enter")
				|| enterButton.getText().equals("I'm sure!")) {
			String userAnswer = answerField.getText();

			// Checks if answer is correct
			int result = test.isCorrect(userAnswer, questionIndex, enterButton
					.getText().equals("Enter") ? experimentalCheck.isSelected()
					: false);
			// Gets the score
			int score = test.getScore();

			if (result == TestFile.PARTIALLY_CORRECT
					|| result == TestFile.COMPLETELY_CORRECT) {
				try {
					// Clip clip = AudioSystem.getClip();
					// clip.open(AudioSystem.getAudioInputStream(new File(
					// "qcorrect.wav")));
					// clip.start();
				} catch (Exception exc) {
					exc.printStackTrace(System.out);

				}
				// Set progress bar colour
				progressBar.setForeground(Color.GREEN);

				if (result == TestFile.COMPLETELY_CORRECT) {
					if (test.getCards().isEmpty()) {
						finishTest();
						// Stop the logic - the test is over!
						return;
					}
					updatePrompt(questionIndex); // prompt label is
													// updated
					updateStatsList();
				}

				// Updates the list of correctly guessed answers
				updateGuessedAnswersList(true, userAnswer);

				System.out.println("Question Index:" + questionIndex);
				progressBar.setString(test.getScore() + "/"
						+ (test.getCards().size() + test.getScore()));

				if (enterButton.getText().equals("I'm sure!")) {
					enterButton.setText("Enter");
				}
				answerField.setText(""); // field is cleared
			} else if (result == TestFile.INCORRECT) {
				progressBar.setForeground(Color.RED);

				test.getCards()
						.get(questionIndex)
						.setGuessedWrong(
								test.getCards().get(questionIndex)
										.getGuessedWrong() + 1);

				// Turn guessedAnswersList red as well
				guessedAnswersList.setForeground(Color.RED);

				// Update guessedAnswersList
				updateGuessedAnswersList(false, userAnswer);

				// Disable some components, change text on Enter button
				switchLanguageCheck.setEnabled(false);
				answerField.setEditable(false);
				passButton.setEnabled(false);
				enterButton.setText("OK");

				// Update statsList
				updateStatsList();

				if (experimentalTimer.isRunning()) {
					promptLabel.setText("<html>"
							+ test.getPrompt(questionIndex) + "</html>");
					experimentalTimer.stop();
				}

				// Reorder cards
				Card c = test.getCards().get(questionIndex);
				Collections.shuffle(test.getCards());
				// If the first card is still the same, move it to the end of
				// the ArrayList
				if (c == test.getCards().get(questionIndex)) {
					test.getCards().remove(c);
					test.getCards().add(c);
				}
				answerField.setText(""); // field is cleared
			} else if (result == TestFile.PROMPT_USER) {
				promptLabel.setText("<html><i>Are you sure? </i></html>");
				enterButton.setText("I'm sure!");
				experimentalTimer.start();
				// Don't clear field here
			}

			progressBar.setValue(score); // sets value of progress bar
		} else if (enterButton.getText().equals("OK")) {
			// Re-enable some components, change text on Enter button back to
			// "Enter"
			switchLanguageCheck.setEnabled(true);
			answerField.setEditable(true);
			answerField.setCaretPosition(0);
			answerField.requestFocusInWindow();
			passButton.setEnabled(true);
			enterButton.setText("Enter");
			// Change guessedAnswersList colour back to normal
			guessedAnswersList.setForeground(textColour);
			// Update prompt label, stats list and totalTimesGuessed
			updatePrompt(questionIndex);
			answerField.setText(""); // field is cleared
		}
	}

	private void finishTest() {
		mainPanel.setVisible(false);
		repaint();
		revalidate();
		// Do not create a new instance of FinishPanel here - otherwise,
		// text-rescaling won't work
		finishPanel.updatePanel();
		framePanel.removeAll();
		framePanel.add(buttonPanel, BorderLayout.NORTH);
		framePanel.add(finishPanel, BorderLayout.CENTER);
		saveButton.setEnabled(false);
		repaint();
		revalidate();
	}

	private void showMainPanel() {
		if (finishPanel != null) {
			framePanel.removeAll();
			framePanel.add(buttonPanel, BorderLayout.NORTH);
			framePanel.add(mainPanel, BorderLayout.CENTER);
			repaint();
			revalidate();
		}

		mainPanel.setVisible(true);
		answerField.setText("");
	}

	private void updateGuessedAnswersList(boolean isCorrect, String userAnswer) {
		// Update guessedAnswersList
		guessedAnswersListModel.removeAllElements();

		// Find out whether to use langFrom or langTo
		ArrayList<String> possibleAnswers = test.isLanguageSwitched() ? test
				.getCards().get(questionIndex).getLangFrom() : test.getCards()
				.get(questionIndex).getLangTo();
		ArrayList<String> correctAnswers = test.isLanguageSwitched() ? test
				.getCards().get(questionIndex).getCorrectLangFrom() : test
				.getCards().get(questionIndex).getCorrectLangTo();

		// Change text depending on whether user got the word right or wrong
		guessedAnswersListModel.addElement("<html><u>"
				+ (isCorrect ? "Already guessed answers: ("
						+ correctAnswers.size() + "/"
						+ (correctAnswers.size() + possibleAnswers.size())
						+ ")" : "All answers:") + "</u></html>");

		// Decide what the list should display based on whether user got the
		// word right or wrong
		for (String s : correctAnswers) {
			guessedAnswersListModel.addElement(s);
		}

		if (!isCorrect) {
			for (String s : possibleAnswers) {
				guessedAnswersListModel.addElement("<html><b><i>" + s
						+ "</i></b></html>");
			}

			if (userAnswer != null) {
				guessedAnswersListModel
						.addElement("<html><u>Your answer:</u></html>");
				guessedAnswersListModel.addElement(userAnswer);
			}
		}
	}

	private void calculateFrameColour(Object[] stats) {
		if ((double) stats[3] >= 95) {
			panelColour = new Color(5, 255, 0);
		} else if ((double) stats[3] >= 90) {
			panelColour = new Color(20, 225, 0);
		} else if ((double) stats[3] >= 80) {
			panelColour = new Color(60, 200, 0);
		} else if ((double) stats[3] >= 70) {
			panelColour = new Color(100, 180, 0);
		} else if ((double) stats[3] >= 60) {
			panelColour = new Color(140, 140, 0);
		} else if ((double) stats[3] >= 50) {
			panelColour = new Color(180, 100, 0);
		} else if ((double) stats[3] >= 40) {
			panelColour = new Color(220, 60, 0);
		} else if ((double) stats[3] >= 30) {
			panelColour = new Color(255, 30, 0);
		} else if ((double) stats[3] >= 20) {
			panelColour = new Color(255, 20, 0);
		} else if ((double) stats[3] >= 0) {
			panelColour = new Color(255, 0, 0);
		}

		int rate = (int) ((double) stats[3]);

		if (!changeBackgroundColour.isEnabled()) {
			// if (rate <= minColour) {
			// updateFrameColour(new Color(255, 0, 0, alpha));
			// } else {
			// updateFrameColour(new Color(255 - (rate / 100 * 255), rate / 100
			// * 255, 0, alpha));
			// }
			updateFrameColour(panelColour);
		}
		progressBar.setForeground(panelColour);
	}

	private void updateFrameColour(Color col) {
		buttonPanel.setBackground(col);
		mainPanel.setBackground(col);
		finishPanel.setBackground(col);
	}

	private void updateStatsList() {
		// { totalNumberOfCards, numberOfIncorrectCards, totalTimesGuessed,
		// successRate }
		Object[] stats = test.getStats();

		// Update statsList
		statsListModel.removeAllElements();
		statsListModel.addElement("<html><u>Statistics:</u></html>");
		// statsListModel.addElement("Answered correctly: "
		// + ((int) stats[0] - test.getCards().size()));
		// statsListModel.addElement("Answered incorrectly: " + stats[1]);
		statsListModel.addElement("Total number of guesses: " + stats[2]);
		statsListModel.addElement("Number of words left: "
				+ test.getCards().size());
		statsListModel.addElement("Success rate: "
				+ String.format("%.2f", (double) stats[3]) + "%");

		calculateFrameColour(stats);
	}

	private void checkForUpdate() {
		try {
			GHRepository repo = GitHub.connectAnonymously().getRepository(
					"duckifyz/VTP5");
			GHRelease release = repo.listReleases().asList().get(0);

			if (release.getTagName().contains(Main.build)
					|| release.getName().contains(Main.build)) {
				JOptionPane
						.showMessageDialog(
								this,
								"You are running the latest version of VTP5. There's no need to worry :)",
								"VTP5", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JEditorPane editorPane = new JEditorPane();
				editorPane.setContentType("text/html");
				setFontSize(editorPane, 20);
				editorPane
						.setText("You are not running the latest release of VTP5. \n\nThe latest version, titled "
								+ release.getName()
								+ ", can be found "
								+ "<a href='https://github.com/duckifyz/VTP5/releases'>here</a>.");

				editorPane.setEditable(false);
				editorPane.setOpaque(false);

				editorPane.addHyperlinkListener(new HyperlinkListener() {
					@Override
					public void hyperlinkUpdate(HyperlinkEvent hle) {
						if (HyperlinkEvent.EventType.ACTIVATED.equals(hle
								.getEventType())) {
							System.out.println(hle.getURL());
							Desktop desktop = Desktop.getDesktop();
							try {
								desktop.browse(hle.getURL().toURI());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});

				JOptionPane.showMessageDialog(this, editorPane, "VTP5",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred:\n\n"
									+ e.toString()
									+ "\n\nYour computer probably isn't connected to the Internet.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void createSettingsFile() {
		properties = new Properties();

		try {

			output = new FileOutputStream("U:/config.properties");

			// set the properties value
			properties.setProperty("experimental",
					String.valueOf(experimentalCheck.isEnabled()));

			// save properties to project root folder
			properties.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	void setUpTest() {
		questionsDialog = new QuestionsDialog(this);
		JOptionPane.showMessageDialog(this, questionsDialog, "VTP5",
				JOptionPane.PLAIN_MESSAGE);

		int limit = test.getCards().size();

		for (int x = 0; x < limit - questionsDialog.slider.getValue(); x++) {
			Collections.shuffle(test.getCards());
			test.getCards().remove(0);
			// test.getOrigCards().remove(0);
		}

		test.totalNumberOfCards = test.getCards().size();

		progressBar.setString(test.getScore() + "/"
				+ (test.getCards().size() + test.getScore()));
		Collections.shuffle(test.getCards());
		updatePrompt(questionIndex);
		panelColour = null;
		updateStatsList();
		progressBar.setMaximum(test.getCards().size() + test.getScore());
		progressBar.setValue(test.getScore());
		progressBar.setForeground(Color.GREEN);
		switchLanguageCheck.setEnabled(true);
		switchLanguageCheck.setSelected(false);
		guessedAnswersList.setForeground(textColour);
		test.setLanguageSwitched(false);
		saveButton.setEnabled(true);
		// leaderboardButton.setEnabled(true);
		answerField.setEditable(true);
		answerField.requestFocus();
		characterButton.setEnabled(true);
		enterButton.setEnabled(true);
		enterButton.setText("Enter");
		passButton.setEnabled(true);
		startAgainButton.setEnabled(true);
		showMainPanel();
	}

	void restartTest() {
		try {
			if (test.getImportedFile() != null) {
				test = new TestFile(new File[] { test.getImportedFile() });
			} else if (test.getOrigCards() != null) {
				test.resetTest();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred:\n\n"
									+ e1.toString()
									+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
		}
	}

	TestFile getTest() {
		return this.test;
	}

	ArrayList<JButton> getButtonList() {
		return buttonList;
	}

	Color getBcolour() {
		return buttonColour;
	}

	Color getFcolour() {
		return fcolour;
	}

	Color getTcolour() {
		return textColour;
	}

	ArrayList<ComponentWithFontData> getComponentList() {
		return componentList;
	}

	private class DisabledItemSelectionModel extends DefaultListSelectionModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setSelectionInterval(int index0, int index1) {
			super.setSelectionInterval(-1, -1);
		}
	}

	// Inner class for the frame's content pane so that the background image can
	// be drawn
	private class FramePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {

			// Graphics2D g2 = (Graphics2D) g;
			// Image backgroundImage = new ImageIcon("res/images/backvtp.png")
			// .getImage();

			// g2.drawImage(backgroundImage, 0, 0, (int) getSize().getWidth(),
			// (int) getSize().getHeight(), 0, 0,
			// (int) backgroundImage.getWidth(this),
			// (int) backgroundImage.getHeight(this), this);
		}
	}

	// FrameListener's componentResized() method will be thrown when the JFrame
	// is resized, so we can scale the text

	// "TIMER IDEA" COURTESY OF
	// http://stackoverflow.com/questions/10229292/get-notified-when-the-user-finishes-resizing-a-jframe
	private class FrameListener extends ComponentAdapter {
		private JFrame frame;
		private Dimension originalSize;

		private double scaler;

		private Timer recalculateTimer;

		private FrameListener(JFrame frame) {
			this.frame = frame;
			// The screen res of Ming's laptop, on which the GUI looks pretty
			// good
			this.originalSize = new Dimension(1366, 768);
			this.recalculateTimer = new Timer(20, new RescaleListener());
			this.recalculateTimer.setRepeats(false);
		}

		@Override
		public void componentResized(ComponentEvent e) {
			if (recalculateTimer.isRunning()) {
				recalculateTimer.restart();
			} else {
				recalculateTimer.start();
			}
		}

		private class RescaleListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Scale the text when the frame is resized
				// Calculate the scale factor
				Dimension newSize = frame.getSize();
				scaler = Math.min(newSize.getWidth() / originalSize.getWidth(),
						newSize.getHeight() / originalSize.getHeight());

				for (ComponentWithFontData c : componentList) {
					Component component = c.getComponent();
					int newFontSize = (int) ((double) c.getOriginalFontSize() * scaler);
					setFontSize(component, newFontSize);
				}

				frame.revalidate();
				frame.repaint();
			}
		}
	}

	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == importFileButton) {
				int option = JOptionPane
						.showOptionDialog(
								getParent(),
								"Do you want to import a text file (for simple tests), a CSV file (for more complex tests),"
										+ "\n                                 or a VTP5 progress file (for partly completed tests)?"
										+ "\n\nTIP: You can combine text files by holding CTRL (or Command on Mac OS) and selecting"
										+ "\n                                                                  multiple text files.",
								"What test type do you want to import?",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, new String[] {
										"Text File", "CSV File",
										"Progress File", "Cancel" }, null);

				// Open JFileChooser and then creates test file
				if (option == 0 || option == 1 || option == 2) {
					showChooserDialog(option);
					try {
						setUpTest();
					} catch (NullPointerException | IndexOutOfBoundsException npe) {
						npe.printStackTrace();
					}
				}

			} else if (e.getSource() == changingFrameColourCheck) {
				if (changingFrameColourCheck.isSelected()) {
					changeBackgroundColour.setEnabled(false);
					if (test != null) {
						updateStatsList();
					}
				} else {
					changeBackgroundColour.setEnabled(true);
					updateFrameColour(null);
				}
			} else if (e.getSource() == changeButtonColour) {
				displayColorChooser(1);
			} else if (e.getSource() == changePromptColour) {
				displayColorChooser(2);
			} else if (e.getSource() == changeTextColour) {
				displayColorChooser(3);
			} else if (e.getSource() == changeBackgroundColour) {
				displayColorChooser(4);
			} else if (e.getSource() == aboutButton) {

				abtDialog.setVisible(true);
			} else if (e.getSource() == enterButton) {
				doLogic();
				revalidate();
				repaint();
			} else if (e.getSource() == passButton) {
				// Reorder cards
				Card c = test.getCards().get(questionIndex);
				Collections.shuffle(test.getCards());
				// If the first card is still the same, move it to the end of
				// the ArrayList
				if (c == test.getCards().get(questionIndex)) {
					test.getCards().remove(c);
					test.getCards().add(c);
				}
				updatePrompt(questionIndex);
				answerField.setText("");
				enterButton.setText("Enter");
			} else if (e.getSource() == helpButton) {
				try {
					java.awt.Desktop
							.getDesktop()
							.browse(new URI(
									"https://github.com/duckifyz/VTP5/wiki/Help"));
				} catch (URISyntaxException | IOException e1) {
					e1.printStackTrace();
					JOptionPane
							.showMessageDialog(
									null,
									"The following error occurred:\n\n"
											+ e1.toString()
											+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
									"VTP5", JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getSource() == settingsButton) {
				settingsDialog.setVisible(true);
				// TODO Finish this
			} else if (e.getSource() == saveButton) {
				// Ultimately, this saves the current TestFile object containing
				// the user's progress data to a .vtp5 file
				// try {
				int answer = progressSaveChooser.showSaveDialog(getParent());
				if (answer == JFileChooser.APPROVE_OPTION) {
					String filePath = progressSaveChooser.getSelectedFile()
							.getAbsolutePath();

					if (!filePath.endsWith(".vtp5")) {
						filePath = filePath + ".vtp5";
					}

					File progressFile = new File(filePath);
					try (ObjectOutputStream output = new ObjectOutputStream(
							new FileOutputStream(progressFile))) {
						output.writeObject(test);
						JOptionPane
								.showMessageDialog(
										null,
										"Success! Your progress has been saved to the following file:\n\n"
												+ filePath
												+ "\n\nTo carry on with this test later, click \"Import Test File\"\nand then click the \"Progress File\" button.",
										"VTP5", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane
								.showMessageDialog(
										null,
										"The following error occurred:\n\n"
												+ e1.toString()
												+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
										"VTP5", JOptionPane.ERROR_MESSAGE);
					}
				}
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
			} else if (e.getSource() == startAgainButton) {
				restartTest();
				setUpTest();
			} else if (e.getSource() == checkForUpdateButton) {
				checkForUpdate();
			} else if (e.getSource() == characterButton) {
				characterDialog.setVisible(true);
			}
		}
	}

	private class ActionEnter extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent ae) {
			enterButton.doClick();
		}
	}

	private class SwitchLanguageListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// Ultimately, this switches langFrom and langTo around, so that the
			// user guesses the langFrom based on the langTo prompt
			test.setLanguageSwitched(switchLanguageCheck.isSelected());

			// Reorder cards
			Card c = test.getCards().get(questionIndex);
			Collections.shuffle(test.getCards());
			// If the first card is still the same, move it to the end of
			// the ArrayList
			if (c == test.getCards().get(questionIndex)) {
				test.getCards().remove(c);
				test.getCards().add(c);
			}

			answerField.setText("");
			enterButton.setText("Enter");
			updatePrompt(questionIndex);
		}

	}
}
