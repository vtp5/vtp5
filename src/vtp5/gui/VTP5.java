package vtp5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;
import vtp5.logic.Card;
import vtp5.logic.TestFile;

public class VTP5 extends JFrame {

	// TODO Generate the serialVersionUID once class has been finished.

	private FramePanel framePanel;

	// Components for button panel at top of frame
	private JPanel buttonPanel;
	private JButton importFileButton, leaderboardButton, settingsButton,
			helpButton, aboutButton, saveButton;
	private int questionIndex = 0;

	// Components in the main area of the frame
	private JPanel mainPanel;
	private JLabel promptLabel;
	// private ArrayList<Card> lang;
	private JTextField answerField;
	private JButton enterButton;
	private JButton passButton;

	private JList<String> statsList;
	private JScrollPane statsScrollPane;
	private DefaultListModel<String> statsListModel;
	private JList<String> guessedAnswersList;
	private JScrollPane guessedAnswersScrollPane;
	private DefaultListModel<String> guessedAnswersListModel;
	private JList<String> correctAnswersList;
	private JScrollPane correctAnswersScrollPane;
	private DefaultListModel<String> correctAnswersListModel;

	public JProgressBar progressBar;
	private JSeparator separator;

	private ArrayList<ComponentWithFontData> componentList = new ArrayList<>();

	private JFileChooser txtChooser = new JFileChooser();
	private JFileChooser csvChooser = new JFileChooser();

	private Color colours[] = { Color.BLACK, Color.WHITE, Color.BLUE,
			Color.CYAN, Color.GRAY };
	private String colourstring[] = { "black", "white", "blue", "cyan", "grey" };

	private JDialog colourd;
	private JLabel butlabel = new JLabel("Button Colour: ");
	private JComboBox<String> buttoncolours = new JComboBox<String>(
			colourstring);
	private JLabel wordbutlabel = new JLabel("Button Label Colour: ");
	private JComboBox<String> wordbutcolours = new JComboBox<String>(
			colourstring);
	private JLabel promptlabel = new JLabel("Prompt Colour: ");
	private JComboBox<String> promptcolours = new JComboBox<String>(
			colourstring);

	// Components for About Dialog
	private JDialog abtDialog;
	private JLabel vtp5Label = new JLabel("Vocab Testing Program 5");
	private JLabel devLabel = new JLabel(
			"Developed by Abdel Abdalla, Minghua Yin,");
	private JLabel dev2Label = new JLabel("Yousuf Ahmed and Nikunj Paliwal.");
	private JLabel wikiLabel = new HyperlinkLabel("Wiki",
			"https://github.com/duckifyz/VTP5/wiki");
	private JLabel srccodeLabel = new HyperlinkLabel("Source Code",
			"https://github.com/duckifyz/VTP5");
	// TODO Create a better icon.
	private ImageIcon logo = new ImageIcon("res/images/vtp.png");
	private ArrayList<JButton> buttonList = new ArrayList<>();

	private TestFile test;

	private Color bcolour = Color.BLACK;
	private Color fcolour = Color.WHITE;
	private Color tcolour = Color.BLACK;

	private static long startTime;

	public Font font;

	public VTP5() {
		// Sets up JFileChooser
		txtChooser.setFileFilter(new FileNameExtensionFilter(
				"Text Files (*.txt)", "txt"));
		csvChooser.setFileFilter(new FileNameExtensionFilter(
				"CSV Files (*csv)", "csv"));

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

		leaderboardButton = new JButton("View Leaderboards");// creates buttons
		leaderboardButton.setBackground(bcolour);// changes background colour
		leaderboardButton.setForeground(fcolour);// changes foreground colour
		leaderboardButton.setEnabled(false);
		buttonList.add(leaderboardButton);

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

		// vtp5Label.setFont(defFont);
		// srccodeLabel.setFont(defFont);
		// devLabel.setFont(defFont);
		// dev2Label.setFont(defFont);

		// Sets up about dialog
		abtDialog = new JDialog(this, "About VTP5");
		abtDialog.setLayout(new MigLayout("fillx"));
		abtDialog.add(new JLabel(logo), "alignx center, aligny top, wrap");
		abtDialog.add(vtp5Label, "alignx center, wrap");
		abtDialog.add(devLabel, "alignx center, wrap");
		abtDialog.add(dev2Label, "alignx center, wrap");
		abtDialog.add(wikiLabel, "alignx center, wrap");
		abtDialog.add(srccodeLabel, "alignx center");
		abtDialog.pack();
		abtDialog.setResizable(false);
		abtDialog.setLocationRelativeTo(this);

		colourd = new JDialog(this, "Settings");
		colourd.setLayout(new MigLayout("fillx"));
		colourd.add(butlabel, "alignx center, wrap");
		colourd.add(buttoncolours, "alignx center, wrap");
		colourd.add(wordbutlabel, "alignx center, wrap");
		colourd.add(wordbutcolours, "alignx center, wrap");
		colourd.add(promptlabel, "alignx center, wrap");
		colourd.add(promptcolours, "alignx center");
		colourd.pack();
		colourd.setResizable(false);
		colourd.setLocationRelativeTo(this);

		separator = new JSeparator();
		separator.setBackground(bcolour);

		componentList.add(new ComponentWithFontData(importFileButton, 34));// adds
		componentList.add(new ComponentWithFontData(saveButton, 34)); // to
		// list
		componentList.add(new ComponentWithFontData(leaderboardButton, 34));// adds
																			// to
																			// list
		componentList.add(new ComponentWithFontData(settingsButton, 34));// adds
																			// to
																			// list
		componentList.add(new ComponentWithFontData(helpButton, 34));// adds to
																		// list
		componentList.add(new ComponentWithFontData(aboutButton, 34));// adds to
																		// list

		// Prevent the buttons from being focusable so there is no ugly
		// rectangle when you click it - this is purely for aesthetic reasons
		importFileButton.setFocusable(false);
		saveButton.setFocusable(false);
		leaderboardButton.setFocusable(false);
		settingsButton.setFocusable(false);
		helpButton.setFocusable(false);
		aboutButton.setFocusable(false);

		importFileButton.addActionListener(new EventListener());
		saveButton.addActionListener(new EventListener());
		aboutButton.addActionListener(new EventListener());
		settingsButton.addActionListener(new EventListener());

		buttonPanel.add(importFileButton, "align left");// adds to panel
		buttonPanel.add(saveButton, "align right");
		buttonPanel.add(leaderboardButton, "align right");// adds to panel
		buttonPanel.add(settingsButton, "align right");// adds to panel
		buttonPanel.add(helpButton, "align right");// adds to panel
		buttonPanel.add(aboutButton, "align right, wrap");// adds to panel
		buttonPanel.add(separator, "span, grow, push");

		// Set up main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("insets 5", "", "[][][]40[]"));

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
		statsListModel.addElement("Statistics:");
		statsList = new JList<>(statsListModel);
		statsList.setVisibleRowCount(5);
		statsList.setForeground(tcolour);// changes text colour
		statsScrollPane = new JScrollPane(statsList);

		guessedAnswersListModel = new DefaultListModel<>();
		guessedAnswersListModel.addElement("Already guessed answers:");
		guessedAnswersList = new JList<>(guessedAnswersListModel);
		guessedAnswersList.setVisibleRowCount(5);
		guessedAnswersList.setForeground(tcolour);// changes text colour
		guessedAnswersScrollPane = new JScrollPane(guessedAnswersList);

		componentList.add(new ComponentWithFontData(statsList, 32));
		componentList.add(new ComponentWithFontData(guessedAnswersList, 32));

		progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 1000);
		progressBar.setValue(0);
		progressBar.setForeground(Color.GREEN);

		// Set the font size of the text in the components
		for (ComponentWithFontData c : componentList) {
			Component component = c.getComponent();
			setFontSize(component, c.getOriginalFontSize());
		}

		// Add components to main panel
		mainPanel.add(promptLabel, "span 3, push, wrap, height 200!");
		mainPanel.add(answerField, "span 2 2, grow");
		mainPanel.add(enterButton, "width 250!, wrap");
		mainPanel.add(passButton, "width 250!, wrap");
		mainPanel.add(statsScrollPane, "grow");
		mainPanel.add(guessedAnswersScrollPane, "grow, push, span");
		mainPanel.add(progressBar, "dock east, width 50!");

		// Add panels to JFrame
		framePanel.add(buttonPanel, BorderLayout.NORTH);
		framePanel.add(mainPanel, BorderLayout.CENTER);
		setContentPane(framePanel);

		// Maximise JFrame
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Sets JFrame properties.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width < 1000 ? screenSize.width : 1000,
				screenSize.height < 650 ? screenSize.height : 650);
		setTitle("VTP5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setIconImage(logo.getImage());
		// Add FrameListener to JFrame so we can detect when the frame is
		// resized

		addComponentListener(new FrameListener(this));

		System.out.println("Boot completed in "
				+ (System.currentTimeMillis() - startTime) + " milliseconds.");
	}

	private void setColor(Color background, Color foreground, Color text) {
		for (JButton b : buttonList) {
			b.setForeground(foreground);
			b.setBackground(background);
		}
		promptLabel.setForeground(text);
		statsList.setForeground(text);
		guessedAnswersList.setForeground(text);

	}

	private void updatePrompt(int index) {
		promptLabel.setText("<html>"
				+ test.getCards().get(index).getLangFrom().get(0) + "</html>");
		updateGuessedAnswersList(true);
	}

	// Method that returns a font object with the "default" font family
	private void setFontSize(Component c, int fontSize) {

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT,
					new FileInputStream("res/fonts/ubuntu/Ubuntu-C.ttf"));
			// font = Font.createFont(Font.TRUETYPE_FONT, Loader
			// .getInputStream("fonts/didactgothic/DidactGothic.ttf"));
			/*
			 * Font font1 = new Font.createFont(Font.TRUETYPE_FONT,
			 * (FileInputStream)Loader.getInputStream(
			 * "res/fonts/didactgothic/DidactGothic.ttf"));
			 */
			font = font.deriveFont((float) fontSize);
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.registerFont(font);
			c.setFont(font);

		} catch (FontFormatException | IOException e) {
			JOptionPane.showMessageDialog(this, "The font file was not found.",
					"VTP5", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

			// Use Arial font (because pretty much everyone has it)
			new Font("Arial", Font.PLAIN, fontSize);
		}
	}

	private void showChooserDialog(int fileType) {
		if (fileType == 0) {
			int selected = txtChooser.showOpenDialog(getParent());
			if (selected == JFileChooser.APPROVE_OPTION) {
				test = new TestFile(txtChooser.getSelectedFile());
				// new Importer(test);
			}
		} else if (fileType == 1) {
			int selected = csvChooser.showOpenDialog(getParent());
			if (selected == JFileChooser.APPROVE_OPTION) {
				test = new TestFile(csvChooser.getSelectedFile());
				// new Importer(test);
			}
		}
	}

	// Inner class for the frame's content pane so that the background image can
	// be drawn
	private class FramePanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			Image backgroundImage = new ImageIcon("res/images/backvtp.png")
					.getImage();

			g2.drawImage(backgroundImage, 0, 0, (int) getSize().getWidth(),
					(int) getSize().getHeight(), 0, 0,
					(int) backgroundImage.getWidth(this),
					(int) backgroundImage.getHeight(this), this);
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

				// System.out.println(System.currentTimeMillis()
				// + ": Getting the size of the frame");

				// Calculate the scale factor
				Dimension newSize = frame.getSize();

				// Printlns for debugging
				// System.out.println("originalSize: " + originalSize);
				// System.out.println("newSize: " + newSize);
				//
				// System.out.println(System.currentTimeMillis()
				// + ": Calculating the scale factor");

				scaler = Math.min(newSize.getWidth() / originalSize.getWidth(),
						newSize.getHeight() / originalSize.getHeight());

				// Printlns for debugging
				// System.out.println("Scaler: " + scaler);

				for (ComponentWithFontData c : componentList) {
					Component component = c.getComponent();
					// System.out.println(System.currentTimeMillis()
					// + ": Currently \"re-sizing\" component "
					// + componentList.indexOf(c) + ": " + component);

					int newFontSize = (int) ((double) c.getOriginalFontSize() * scaler);

					// Printlns for debugging:
					// System.out.println("newFontSize: " + newFontSize);

					setFontSize(component, newFontSize);
				}

				frame.revalidate();
				frame.repaint();

				// System.out
				// .println("****************************************************************");
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
								"Do you want to import a text file (for simple tests) or a CSV file (for more complex tests)?",
								"What test type do you want to import?",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, new String[] {
										"Text File", "CSV File", "Cancel" },
								null);
				// Open JFileChooser and then creates test file
				if (option == 0 || option == 1) {
					showChooserDialog(option);
					try {
						Collections.shuffle(test.getCards());
						updatePrompt(questionIndex);
						progressBar.setValue(0);
						progressBar.setMaximum(test.getCards().size());

						saveButton.setEnabled(true);
						leaderboardButton.setEnabled(true);
						enterButton.setEnabled(true);
						passButton.setEnabled(true);
					} catch (NullPointerException npe) {
						JOptionPane.showMessageDialog(getParent(),
								"No file selected.", "VTP5",
								JOptionPane.INFORMATION_MESSAGE);

					}
				}

			} else if (e.getSource() == buttoncolours) {
				int i = buttoncolours.getSelectedIndex();
				bcolour = colours[i];

				setColor(bcolour, fcolour, tcolour);

			} else if (e.getSource() == wordbutcolours) {
				int i = wordbutcolours.getSelectedIndex();
				fcolour = colours[i];

				setColor(bcolour, fcolour, tcolour);

			} else if (e.getSource() == promptcolours) {
				int i = promptcolours.getSelectedIndex();
				tcolour = colours[i];

				setColor(bcolour, fcolour, tcolour);

			}

			else if (e.getSource() == aboutButton) {
				abtDialog.setVisible(true);
			} else if (e.getSource() == enterButton) {
				doLogic();
			} else if (e.getSource() == settingsButton) {
				colourd.setVisible(true); // colour settings is displayed
				buttoncolours.addActionListener(this);
				wordbutcolours.addActionListener(this);
				promptcolours.addActionListener(this);

				// TODO

			} else if (e.getSource() == passButton) {
				Collections.shuffle(test.getCards()); // Reorder cards
				updatePrompt(questionIndex);
			} else if (e.getSource() == saveButton) {

				try {
					JFileChooser chooser = new JFileChooser();
					int answer = chooser.showSaveDialog(getParent());

					if (answer == JFileChooser.APPROVE_OPTION) {
						FileWriter fwriter = new FileWriter(
								chooser.getSelectedFile() + ".txt"); // filewriter
																		// for
																		// .txt
																		// created
						BufferedWriter bfwriter = new BufferedWriter(fwriter); // parsed
																				// to
																				// buffered
																				// writer
						for (Card s : test.getCards()) { // card is looped
															// through
							bfwriter.write(s.getLangFrom().get(0)); // prompt is
																	// written
							bfwriter.newLine(); // new line
							bfwriter.write(s.getLangTo().get(0)); // answer is
																	// written
							bfwriter.newLine();
							System.out.println("saved");
						}
						bfwriter.close(); // writer is closed
						System.out.println("File saved");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}

	}

	private void doLogic() {
		if (enterButton.getText().equals("Enter")) {
			int result = test.isCorrect(answerField.getText(), questionIndex);// checks
																				// if
																				// the
																				// answer
																				// is
																				// correct

			// Gets the score
			int score = test.getScore();

			if (result == TestFile.PARTIALLY_CORRECT
					|| result == TestFile.COMPLETELY_CORRECT) {
				progressBar.setForeground(Color.GREEN); // changes the
														// colour
														// of the
														// progress
														// bar

				// Updates the list of correctly guessed answers
				updateGuessedAnswersList(true);

				if (result == TestFile.COMPLETELY_CORRECT) {
					test.getCards().remove(questionIndex); // removes
															// the
															// card
															// after it
															// is
															// answered.

					if (test.getCards().isEmpty()) { // checks if the
														// arraylist
														// of cards is
														// empty
						JOptionPane.showMessageDialog(null, "You win"); // displays
																		// that
																		// you
																		// have
																		// won
					}

					updatePrompt(questionIndex); // prompt label is
													// updated
				}

				System.out.println("Question Index:" + questionIndex);

			} else if (result == TestFile.INCORRECT) { // if
														// answer
														// is
														// incorrect
				progressBar.setForeground(Color.RED); // progress bar
														// turns
														// red

				// Turn guessedAnswersList red as well
				guessedAnswersList.setForeground(Color.RED);

				// Update guessedAnswersList
				updateGuessedAnswersList(false);

				// Disable some components, change text on Enter button
				answerField.setEditable(false);
				passButton.setEnabled(false);
				enterButton.setText("OK");

				// Shuffle cards
				Collections.shuffle(test.getCards());
			}
			progressBar.setValue(score); // sets value of progress bar
		} else {
			// Re-enable some components, change text on Enter button back to
			// "Enter"
			answerField.setEditable(true);
			answerField.setCaretPosition(0);
			answerField.requestFocusInWindow();
			passButton.setEnabled(true);
			enterButton.setText("Enter");

			// Change guessedAnswersList colour back to normal
			guessedAnswersList.setForeground(tcolour);

			// Update prompt label
			updatePrompt(questionIndex);
		}

		answerField.setText(""); // field is cleared
	}

	private void updateGuessedAnswersList(boolean isCorrect) {
		guessedAnswersListModel.removeAllElements();
		// Change text depending on whether user got the word right or wrong
		guessedAnswersListModel
				.addElement(isCorrect ? "Already guessed answers: ("
						+ test.getCards().get(questionIndex).getCorrectLangTo()
								.size()
						+ "/"
						+ (test.getCards().get(questionIndex)
								.getCorrectLangTo().size() + test.getCards()
								.get(questionIndex).getLangTo().size()) + ")"
						: "Correct answers:");

		// Decide what the list should display based on whether user got the
		// word right or wrong
		for (String s : test.getCards().get(questionIndex).getCorrectLangTo()) {
			guessedAnswersListModel.addElement(s);
		}

		if (!isCorrect) {
			for (String s : test.getCards().get(questionIndex).getLangTo()) {
				guessedAnswersListModel.addElement(s);
			}
		}
	}

	private class ActionEnter extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5999070856026958307L;

		@Override
		public void actionPerformed(ActionEvent ae) {

			enterButton.doClick();
		}

	}

	private class HyperlinkLabel extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 896828172865617940L;

		public HyperlinkLabel(String text, final String link) {
			this.setText("<html><a href=\"" + link + "\">" + text
					+ "</a></html>");
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						java.awt.Desktop.getDesktop().browse(new URI(link));
					} catch (URISyntaxException | IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				startTime = System.currentTimeMillis();
				// TODO Find out why it takes so long from here to the start of
				// the VTP5 obj

				new VTP5();

			}
		});
	}
}
