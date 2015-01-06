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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
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

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import net.miginfocom.swing.MigLayout;
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

	// Components for Settings Dialog
	private JDialog settingsDialog;
	private JButton changeButtonColour, changePromptColour,
			changeForegroundColour, checkForUpdateButton;
	private JCheckBox experimentalCheck;
	private HyperlinkLabel exInfoLabel;

	// Components for About Dialog
	private AboutDialog abtDialog;

	private ImageIcon logo = new ImageIcon(getClass().getResource(
			"/images/vtp.png"));
	private ArrayList<JButton> buttonList = new ArrayList<>();

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

	private Color bcolour = Color.BLACK;
	private Color fcolour = Color.WHITE;
	private Color tcolour = Color.BLACK;

	// finishPanel instance variable - must create the object HERE (i.e. as soon
	// as program begins), otherwise text-rescaling won't work properly
	private FinishPanel finishPanel = new FinishPanel(this);

	public Font font;

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

		framePanel = new FramePanel();// make primary panel
		framePanel.setLayout(new BorderLayout());// set layout

		// Set up button panel
		buttonPanel = new JPanel();// make panel for buttons
		buttonPanel.setLayout(new MigLayout());// set layout

		importFileButton = new JButton("Import Test File");// creates buttons
		importFileButton.setBackground(bcolour);// changes background colour
		importFileButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(importFileButton);

		saveButton = new JButton("Complete Later");
		saveButton.setBackground(bcolour);
		saveButton.setForeground(fcolour);
		saveButton.setEnabled(false);
		buttonList.add(saveButton);

		startAgainButton = new JButton("Start Again");
		startAgainButton.setBackground(bcolour);
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
		settingsButton.setBackground(bcolour);// changes background colour
		settingsButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(settingsButton);

		helpButton = new JButton("Help");// ads button
		helpButton.setBackground(bcolour);// changes background colour
		helpButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(helpButton);

		aboutButton = new JButton("About");// creates buttons
		aboutButton.setBackground(bcolour);// changes background colour
		aboutButton.setForeground(fcolour);// changes foreground colour
		buttonList.add(aboutButton);

		// Sets up about dialog
		abtDialog = new AboutDialog();

		changeButtonColour = new JButton("Change Button Colour");
		changePromptColour = new JButton("Change Prompt Colour");
		changeForegroundColour = new JButton("Change Button Text Colour");
		checkForUpdateButton = new JButton("Check For Update");
		experimentalCheck = new JCheckBox("Enable experimental features");
		exInfoLabel = new HyperlinkLabel(
				"<html>Click here for more information<br />on experimental features</html>",
				"https://github.com/duckifyz/VTP5/wiki/Help#experimental-features");

		settingsDialog = new JDialog(this, "Settings");
		settingsDialog.setLayout(new MigLayout("fillx", "", "[][][]10[]10[]"));
		settingsDialog.add(checkForUpdateButton, "alignx center, wrap");
		settingsDialog.add(new JSeparator(), "grow, wrap");
		settingsDialog.add(changeButtonColour, "alignx center, wrap");
		settingsDialog.add(changePromptColour, "alignx center, wrap");
		settingsDialog.add(changeForegroundColour, "alignx center, wrap");
		settingsDialog.add(new JSeparator(), "grow, wrap");
		settingsDialog.add(experimentalCheck, "alignx center, wrap");
		settingsDialog.add(exInfoLabel);
		settingsDialog.pack();
		settingsDialog.setResizable(false);
		settingsDialog.setLocationRelativeTo(this);

		separator = new JSeparator();
		separator.setBackground(bcolour);

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
		changeForegroundColour.addActionListener(eventListener);
		checkForUpdateButton.addActionListener(eventListener);

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

		switchLanguageCheck = new JCheckBox("Switch language");
		switchLanguageCheck.setFocusable(false);
		switchLanguageCheck.setForeground(tcolour);
		switchLanguageCheck.setBackground(Color.GRAY);
		switchLanguageCheck.setEnabled(false);
		switchLanguageCheck.addItemListener(new SwitchLanguageListener());
		componentList.add(new ComponentWithFontData(switchLanguageCheck, 30));

		promptLabel = new JLabel(
				"<html>Click 'Import Test File' to begin.</html>");// creates
		// label
		promptLabel.setForeground(tcolour);// changes text colour

		answerField = new JTextField();// creates text field
		answerField.addActionListener(new EventListener());
		answerField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("released ENTER"), "Enter");
		answerField.getActionMap().put("Enter", new ActionEnter());
		answerField.setForeground(tcolour);// changes text colour

		componentList.add(new ComponentWithFontData(promptLabel, 72));// adds to
																		// list
		componentList.add(new ComponentWithFontData(answerField, 50));// adds to
		// list

		enterButton = new JButton("Enter");// creates buttons
		enterButton.addActionListener(new EventListener());
		buttonList.add(enterButton);

		enterButton.setBackground(bcolour);// changes background colour
		enterButton.setForeground(fcolour);// changes foreground colour
		enterButton.setEnabled(false);

		componentList.add(new ComponentWithFontData(enterButton, 32));// adds to
																		// list

		passButton = new JButton("Skip");// creates buttons
		passButton.setBackground(bcolour);// changes background colour
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

		// Set up JLists and their respective ListModels
		statsListModel = new DefaultListModel<>();
		statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Answered correctly: ");
		statsListModel.addElement("Answered incorrectly: ");
		statsListModel.addElement("Number of words left: ");
		statsListModel.addElement("Total number of guesses: ");
		statsListModel.addElement("Success rate: ");
		statsList = new JList<>(statsListModel);
		statsList.setVisibleRowCount(6);
		statsList.setForeground(tcolour);// changes text colour
		statsScrollPane = new JScrollPane(statsList);

		guessedAnswersListModel = new DefaultListModel<>();
		guessedAnswersListModel
				.addElement("<html><u>Already guessed answers:</u></html>");
		guessedAnswersList = new JList<>(guessedAnswersListModel);
		guessedAnswersList.setVisibleRowCount(6);
		guessedAnswersList.setForeground(tcolour);// changes text colour
		guessedAnswersScrollPane = new JScrollPane(guessedAnswersList);

		componentList.add(new ComponentWithFontData(statsList, 32));
		componentList.add(new ComponentWithFontData(guessedAnswersList, 32));

		// Set the font size of the text in the components
		for (ComponentWithFontData c : componentList) {
			Component component = c.getComponent();
			setFontSize(component, c.getOriginalFontSize());
		}

		// Add components to main panel
		mainPanel.add(promptLabel, "span 3, push, wrap, height 30%!");
		mainPanel.add(switchLanguageCheck, "wrap");
		mainPanel.add(answerField, "span 2 2, grow");
		mainPanel.add(enterButton, "width 250!, wrap");
		mainPanel.add(passButton, "width 250!, wrap");
		mainPanel.add(statsScrollPane, "grow, width 35%!");
		mainPanel.add(guessedAnswersScrollPane,
				"width 60%!, grow, push, span 2");
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
					test = new TestFile(txtChooser.getSelectedFile());
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
		Color c;
		switch (index) {
		case 1:
			c = JColorChooser.showDialog(null, "Choose a colour", buttonList
					.get(0).getBackground());
			if (c != null) {
				setColour(c, bcolour, tcolour);
				bcolour = c;
				c = null;
			}
			break;
		case 2:
			c = JColorChooser.showDialog(null, "Choose a colour",
					promptLabel.getForeground());
			if (c != null) {
				setColour(bcolour, fcolour, c);
				tcolour = c;
				c = null;
			}
			break;
		case 3:
			c = JColorChooser.showDialog(null, "Choose a colour", buttonList
					.get(0).getForeground());
			if (c != null) {
				setColour(bcolour, c, tcolour);
				fcolour = c;
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

				// Turn guessedAnswersList red as well
				guessedAnswersList.setForeground(Color.RED);

				// Update guessedAnswersList
				updateGuessedAnswersList(false, userAnswer);

				// Disable some components, change text on Enter button
				answerField.setEditable(false);
				passButton.setEnabled(false);
				enterButton.setText("OK");

				// Update statsList
				updateStatsList();

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
			answerField.setEditable(true);
			answerField.setCaretPosition(0);
			answerField.requestFocusInWindow();
			passButton.setEnabled(true);
			enterButton.setText("Enter");
			// Change guessedAnswersList colour back to normal
			guessedAnswersList.setForeground(tcolour);
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
						+ ")" : "Correct answers:") + "</u></html>");

		// Decide what the list should display based on whether user got the
		// word right or wrong
		for (String s : correctAnswers) {
			guessedAnswersListModel.addElement(s);
		}

		if (!isCorrect) {
			for (String s : possibleAnswers) {
				guessedAnswersListModel.addElement(s);
			}

			if (userAnswer != null) {
				guessedAnswersListModel
						.addElement("<html><u>Your answer:</u></html>");
				guessedAnswersListModel.addElement(userAnswer);
			}
		}
	}

	private void updateStatsList() {
		// { totalNumberOfCards, numberOfIncorrectCards, totalTimesGuessed,
		// successRate }
		Object[] stats = test.getStats();

		// Update statsList
		statsListModel.removeAllElements();
		statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Answered correctly: "
				+ ((int) stats[0] - test.getCards().size()));
		statsListModel.addElement("Answered incorrectly: " + stats[1]);
		statsListModel.addElement("Total number of guesses: " + stats[2]);
		statsListModel.addElement("Number of words left: "
				+ test.getCards().size());
		statsListModel.addElement("Success rate: "
				+ String.format("%.2f", (double) stats[3]) + "%");
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
			e.printStackTrace();
		}
	}

	void setUpTest() {
		progressBar.setString(test.getScore() + "/"
				+ (test.getCards().size() + test.getScore()));
		Collections.shuffle(test.getCards());
		updatePrompt(questionIndex);
		updateStatsList();
		progressBar.setMaximum(test.getCards().size() + test.getScore());
		progressBar.setValue(test.getScore());
		progressBar.setForeground(Color.GREEN);
		switchLanguageCheck.setEnabled(true);
		switchLanguageCheck.setSelected(false);
		test.setLanguageSwitched(false);
		saveButton.setEnabled(true);
		// leaderboardButton.setEnabled(true);
		enterButton.setEnabled(true);
		enterButton.setText("Enter");
		passButton.setEnabled(true);
		startAgainButton.setEnabled(true);
		showMainPanel();
	}

	TestFile getTest() {
		return this.test;
	}

	ArrayList<JButton> getButtonList() {
		return buttonList;
	}

	Color getBcolour() {
		return bcolour;
	}

	Color getFcolour() {
		return fcolour;
	}

	Color getTcolour() {
		return tcolour;
	}

	ArrayList<ComponentWithFontData> getComponentList() {
		return componentList;
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
			this.originalSize = frame.getSize();
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
								"Do you want to import a text file (for simple tests), a CSV file (for more complex tests),\n                                 or a VTP5 progress file (for partly completed tests)?",
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
						JOptionPane.showMessageDialog(getParent(),
								"No file selected.", "VTP5",
								JOptionPane.INFORMATION_MESSAGE);
						npe.printStackTrace();
					}
				}

			} else if (e.getSource() == changeButtonColour) {
				displayColorChooser(1);
			} else if (e.getSource() == changePromptColour) {
				displayColorChooser(2);
			} else if (e.getSource() == changeForegroundColour) {
				displayColorChooser(3);
			} else if (e.getSource() == aboutButton) {
				abtDialog.setVisible(true);
			} else if (e.getSource() == enterButton) {
				doLogic();
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
					// FileWriter fwriter = new FileWriter(
					// chooser.getSelectedFile() + ".txt"); // filewriter
					// // for
					// // .txt
					// // created
					// BufferedWriter bfwriter = new BufferedWriter(fwriter); //
					// parsed
					// // to
					// // buffered
					// // writer
					// for (Card s : test.getCards()) { // card is looped
					// // through
					// bfwriter.write(s.getLangFrom().get(0)); // prompt is
					// // written
					// bfwriter.newLine(); // new line
					// bfwriter.write(s.getLangTo().get(0)); // answer is
					// // written
					// bfwriter.newLine();
					// System.out.println("saved");
					// }
					// bfwriter.close(); // writer is closed
					// System.out.println("File saved");
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
				try {
					test = new TestFile(test.getImportedFile());
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
				setUpTest();
			} else if (e.getSource() == checkForUpdateButton) {
				checkForUpdate();
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
