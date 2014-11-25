package vtp5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
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
	// Test
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FramePanel framePanel;

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

	private ArrayList<ComponentWithFontData> componentList = new ArrayList<>();

	private JFileChooser chooser = new JFileChooser();

	private FileNameExtensionFilter chooserFilter = new FileNameExtensionFilter(
			"Text Files (*.txt)", "txt");

	TestFile test;

	Color bcolour = Color.BLACK;
	Color fcolour = Color.WHITE;
	Color tcolour = Color.BLACK;

	public VTP5() {
		// Sets up JFileChooser
		chooser.setFileFilter(chooserFilter);

		framePanel = new FramePanel();// make primary panel
		framePanel.setLayout(new BorderLayout());// set layout

		// Set up button panel
		buttonPanel = new JPanel();// make panel for buttons
		buttonPanel.setLayout(new MigLayout());// set layout

		importFileButton = new JButton("Import Test File");// creates buttons
		importFileButton.setBackground(bcolour);// changes background colour
		importFileButton.setForeground(fcolour);// changes foreground colour

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

		componentList.add(new ComponentWithFontData(importFileButton, 34));// adds
																			// to
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
		leaderboardButton.setFocusable(false);
		settingsButton.setFocusable(false);
		helpButton.setFocusable(false);
		aboutButton.setFocusable(false);

		importFileButton.addActionListener(new EventListener());
		aboutButton.addActionListener(new EventListener());

		buttonPanel.add(importFileButton, "align left");// adds to panel
		buttonPanel.add(leaderboardButton, "push, align right");// adds to panel
		buttonPanel.add(settingsButton, "align right");// adds to panel
		buttonPanel.add(helpButton, "align right");// adds to panel
		buttonPanel.add(aboutButton, "align right, wrap");// adds to panel
		buttonPanel.add(new JSeparator(), "span, grow, push");

		// Set up main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("insets 5", "", "[][][]40[]"));

		promptLabel = new JLabel(
				"<html><div style=\"text-align:center;\">Prompt</div></html>");// creates
																				// label
		promptLabel.setForeground(tcolour);// changes text colour

		answerField = new JTextField("Enter answer here");// creates text field
		answerField.addActionListener(new EventListener());
		answerField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("ENTER"), "Enter");
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

		componentList.add(new ComponentWithFontData(enterButton, 36));// adds to
																		// list

		passButton = new JButton("Pass");// creates buttons
		passButton.setBackground(bcolour);// changes background colour
		passButton.setForeground(fcolour);// changes foreground colour

		componentList.add(new ComponentWithFontData(passButton, 36));// adds to
																		// list

		// Prevent the buttons from being focusable so there is no ugly
		// rectangle when you click it - this is purely for aesthetic reasons
		enterButton.setFocusable(false);
		passButton.setFocusable(false);

		// Set up JLists and their respective ListModels
		statsList = new JList<>(new String[] { "Stats:", "1", "2", "3", "4" });
		statsList.setVisibleRowCount(5);
		statsList.setForeground(tcolour);// changes text colour
		statsScrollPane = new JScrollPane(statsList);

		guessedAnswersList = new JList<>(new String[] {
				"Already guessed answers:", "1", "2", "3", "4" });
		guessedAnswersList.setVisibleRowCount(5);
		guessedAnswersList.setForeground(tcolour);// changes text colour
		guessedAnswersScrollPane = new JScrollPane(guessedAnswersList);

		componentList.add(new ComponentWithFontData(statsList, 30));
		componentList.add(new ComponentWithFontData(guessedAnswersList, 30));

		progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
		progressBar.setValue(50);
		progressBar.setForeground(Color.GREEN);

		// Set the font size of the text in the components
		for (ComponentWithFontData c : componentList) {
			Component component = c.getComponent();
			setFontSize(component, c.getOriginalFontSize());
		}

		// Add components to main panel
		mainPanel.add(promptLabel, "span 3, push, wrap, height 200!");
		mainPanel.add(answerField, "span 2 2, grow");
		mainPanel.add(enterButton, "width 250, wrap");
		mainPanel.add(passButton, "width 250, wrap");
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
		addMouseListener(new MouseListener());
		setSize(800, 600);
		setTitle("VTP5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		// Add FrameListener to JFrame so we can detect when the frame is
		// resized

		addComponentListener(new FrameListener(this));
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
	private class FrameListener extends ComponentAdapter {
		private JFrame frame;
		private Dimension originalSize;

		private FrameListener(JFrame frame) {
			this.frame = frame;
			this.originalSize = frame.getSize();
		}

		@Override
		public void componentResized(ComponentEvent e) {
			// Scale the text when the frame is resized

			// Calculate the scale factor
			Dimension newSize = frame.getSize();

			// Printlns for debugging
			// System.out.println("originalSize: " + originalSize);
			// System.out.println("newSize: " + newSize);

			double scaler = Math.min(
					newSize.getWidth() / originalSize.getWidth(),
					newSize.getHeight() / originalSize.getHeight());

			// Printlns for debugging
			// System.out.println("Scaler: " + scaler);

			for (ComponentWithFontData c : componentList) {
				Component component = c.getComponent();

				int newFontSize = (int) ((double) c.getOriginalFontSize() * scaler);

				// Printlns for debugging:
				// System.out.println("newFontSize: " + newFontSize);

				setFontSize(component, newFontSize);
				frame.revalidate();
				frame.repaint();
			}
		}
	}

	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == importFileButton) {
				// Open JFileChooser and then creates test file
				int selected = chooser.showOpenDialog(getParent());
				if (selected == JFileChooser.APPROVE_OPTION) {
					// Show confirmation dialog if currently in a test.
					// If so, clear old test.
					test = new TestFile(chooser.getSelectedFile());
				}
			} else if (e.getSource() == aboutButton) {
				try {
					java.awt.Desktop
							.getDesktop()
							.browse(new URI(
									"https://github.com/duckifyz/VTP5/wiki/Developers"));
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (e.getSource() == enterButton
					|| e.getSource() == answerField) {
				System.out.println("Enter");
			}
		}

	}

	private class MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {

			// System.out.println(answerField.getBounds().getMinX());
			// System.out.println(answerField.getBounds().getMaxX());
			// System.out.println(answerField.getBounds().getMinY());
			// System.out.println(answerField.getBounds().getMaxY());
			//
			// System.out.println();
			// System.out.println(e.getX() + ", " + e.getY());
			//
			// if (((e.getX() >= answerField.getBounds().getMinX()) && (e.getX()
			// <= answerField
			// .getBounds().getMaxX()))
			// && ((e.getY() >= answerField.getBounds().getMinX()) && (e
			// .getY() <= answerField.getBounds().getMaxY()))) {
			//
			// if (answerField.getText().equals("Enter answer here")) {
			// answerField.setText("");
			// }
			// }
			//
			if (answerField.getText().equals("Enter answer here")) {
				if (enterButton.getX() - e.getX() < 500
						&& enterButton.getY() - e.getY() < 200) {
					answerField.setText("");
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

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VTP5();
			}
		});
	}

}
