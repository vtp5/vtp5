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

	public JProgressBar progressBar;
	private JSeparator separator;

	private ArrayList<ComponentWithFontData> componentList = new ArrayList<>();

	private JFileChooser txtChooser = new JFileChooser();
	private JFileChooser csvChooser = new JFileChooser();

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

	private TestFile test;

	private Color bcolour = Color.BLACK;
	private Color fcolour = Color.WHITE;
	private Color tcolour = Color.BLACK;

	private int score = 0;

	private static long startTime;

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

		saveButton = new JButton("Complete Later");
		saveButton.setBackground(bcolour);
		saveButton.setForeground(fcolour);

		leaderboardButton = new JButton("View Leaderboards");// creates buttons
		leaderboardButton.setBackground(bcolour);// changes background colour
		leaderboardButton.setForeground(fcolour);// changes foreground colour

		settingsButton = new JButton("Settings");// creates buttons
		settingsButton.setBackground(bcolour);// changes background colour
		settingsButton.setForeground(fcolour);// changes foreground colour

		helpButton = new JButton("Help");// ads button
		helpButton.setBackground(bcolour);// changes background colour
		helpButton.setForeground(fcolour);// changes foreground colour

		aboutButton = new JButton("About");// creates buttons
		aboutButton.setBackground(bcolour);// changes background colour
		aboutButton.setForeground(fcolour);// changes foreground colour

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

		promptLabel = new JLabel("Click 'Import Text File' to begin.");// creates
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

		enterButton.setBackground(bcolour);// changes background colour
		enterButton.setForeground(fcolour);// changes foreground colour

		componentList.add(new ComponentWithFontData(enterButton, 32));// adds to
																		// list

		passButton = new JButton("Pass");// creates buttons
		passButton.setBackground(bcolour);// changes background colour
		passButton.setForeground(fcolour);// changes foreground colour
		passButton.addActionListener(new EventListener());

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
		progressBar.setValue(score);
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

		// Add FrameListener to JFrame so we can detect when the frame is
		// resized

		addComponentListener(new FrameListener(this));

		System.out.println("Boot completed in "
				+ (System.currentTimeMillis() - startTime) + " milliseconds.");
	}

	private void updatePrompt(int index) {
		promptLabel.setText(test.getCards().get(index).getLangFrom().get(0));
	}

	// Method that returns a font object with the "default" font family
	private void setFontSize(Component c, int fontSize) {

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT,
					new FileInputStream("res/fonts/DidactGothic.ttf"));
			font = font.deriveFont((float) fontSize);
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

				System.out.println(System.currentTimeMillis()
						+ ": Getting the size of the frame");

				// Calculate the scale factor
				Dimension newSize = frame.getSize();

				// Printlns for debugging
				System.out.println("originalSize: " + originalSize);
				System.out.println("newSize: " + newSize);

				System.out.println(System.currentTimeMillis()
						+ ": Calculating the scale factor");

				scaler = Math.min(newSize.getWidth() / originalSize.getWidth(),
						newSize.getHeight() / originalSize.getHeight());

				// Printlns for debugging
				System.out.println("Scaler: " + scaler);

				for (ComponentWithFontData c : componentList) {
					Component component = c.getComponent();
					System.out.println(System.currentTimeMillis()
							+ ": Currently \"re-sizing\" component "
							+ componentList.indexOf(c) + ": " + component);

					int newFontSize = (int) ((double) c.getOriginalFontSize() * scaler);

					// Printlns for debugging:
					System.out.println("newFontSize: " + newFontSize);

					setFontSize(component, newFontSize);
				}

				frame.revalidate();
				frame.repaint();

				System.out
						.println("****************************************************************");
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
					Collections.shuffle(test.getCards());
					updatePrompt(questionIndex);
				}
				progressBar.setMaximum(test.getCards().size());

			} else if (e.getSource() == aboutButton) {
				abtDialog.setVisible(true);
			} else if (e.getSource() == enterButton) {
				score = test.updateScore(answerField.getText(), questionIndex,
						score);
				if (test.isCorrect(answerField.getText(), questionIndex)) {
					progressBar.setForeground(Color.GREEN);
					test.getCards().remove(0);
					questionIndex++;
				} else if (!test
						.isCorrect(answerField.getText(), questionIndex)) {
					progressBar.setForeground(Color.RED);
				}
				progressBar.setValue(score);
				updatePrompt(questionIndex);
				answerField.setText("");
			} else if (e.getSource() == settingsButton) {

			} else if (e.getSource() == passButton) {
				questionIndex++;
				updatePrompt(questionIndex);
			} else if (e.getSource() == saveButton) {

				/*
				 * try { FileOutputStream fsos = new FileOutputStream("output");
				 * 
				 * ObjectOutputStream oos = new ObjectOutputStream(fsos);
				 * oos.writeObject(test.getCards()); // write MenuArray to
				 * ObjectOutputStream oos.close();
				 * 
				 * } catch (IOException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); } }
				 */
				try {
					JFileChooser chooser = new JFileChooser();
					int answer = chooser.showSaveDialog(null);
					if (answer == JFileChooser.APPROVE_OPTION) {
						FileWriter fwriter = new FileWriter(
								chooser.getSelectedFile() + ".txt");
						BufferedWriter bfwriter = new BufferedWriter(fwriter);
						for (Card s : test.getCards()) {
							bfwriter.write(s.getLangFrom().get(0));
							bfwriter.newLine();
							bfwriter.write(s.getLangTo().get(0));
							bfwriter.newLine();
							System.out.println("saved");
						}
						bfwriter.close();
						System.out.println("File saved");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}

	}

	private class ActionEnter extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ae) {

			enterButton.doClick();
		}

	}

	private class HyperlinkLabel extends JLabel {

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
