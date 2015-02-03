package vtp5.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXLabel;

import net.miginfocom.swing.MigLayout;
import vtp5.logic.Card;
import vtp5.logic.TestFile;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;

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
public class FinishPanel extends WebPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String screenloc;

	private JXLabel completedLabel = new JXLabel();
	private JXLabel showListLabel = new JXLabel();
	private CustomFont cf;
	private WebTable table = new WebTable();
	private TestFile test;
	private String completedMessage;
	private DefaultListModel<Object> statsListModel = new DefaultListModel<>();
	private JList<Object> statsList = new JList<>(statsListModel);
	private WebScrollPane statsScrollPane = new WebScrollPane(statsList);
	private WrongAnswersTableModel watm;
	private VTP5Button saveTest;
	private VTP5Button restartTest;
	private VTP5Button screenshotButton;
	private JFileChooser wrongAnswersTest = new JFileChooser();
	private TableRowSorter<AbstractTableModel> sorter;

	// Do not remove this variable - it makes text-rescaling work!
	private VTP5 parent;

	// TODO Creating JList for leader boards (WIP)
	private JList<Object> leaderboards = new JList<>();

	private JFileChooser imagesave = new JFileChooser();

	public FinishPanel(final VTP5 parent) {
		this.parent = parent;

		imagesave.setFileFilter(new FileNameExtensionFilter(
				"PNG Files (*.png)", "png"));

		setLayout(new MigLayout("fillx"));

		saveTest = new VTP5Button("Save Wrong Answers to Test File", parent);
		restartTest = new VTP5Button("Start Again", parent);
		screenshotButton = new VTP5Button("Take Screenshot", parent);

		saveTest.setFocusable(false);
		restartTest.setFocusable(false);
		screenshotButton.setFocusable(false);

		setTextColour(parent.getTextColour());

		statsList.setSelectionModel(new DisabledItemSelectionModel());

		cf = new CustomFont();
		cf.setFont(completedLabel, 75);
		cf.setFont(showListLabel, 40);
		cf.setFont(statsList, 40);
		cf.setFont(table, 30);
		cf.setFont(table.getTableHeader(), 30);
		cf.setFont(leaderboards, 40);
		cf.setFont(saveTest, 40);
		cf.setFont(restartTest, 40);

		// Add components to componentList in VTP5 class so that font rescaling
		// can occur when frame is resized
		ArrayList<ComponentWithFontData> componentList = parent
				.getComponentList();
		componentList.add(new ComponentWithFontData(completedLabel, 75));
		componentList.add(new ComponentWithFontData(showListLabel, 40));
		componentList.add(new ComponentWithFontData(statsList, 40));
		componentList.add(new ComponentWithFontData(table, 30));
		componentList
				.add(new ComponentWithFontData(table.getTableHeader(), 30));
		componentList.add(new ComponentWithFontData(leaderboards, 40));
		componentList.add(new ComponentWithFontData(saveTest, 40));
		componentList.add(new ComponentWithFontData(restartTest, 40));
		componentList.add(new ComponentWithFontData(screenshotButton, 40));

		// TODO Proper exception handling for the two ActionListeners below

		restartTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(parent,
						"Are you sure you want to start again?", "VTP5",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					parent.restartTest();
					parent.setUpTest(0);
				}
			}
		});

		saveTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parent.getTest().getIncorrectCards().size() == 0) {
					JOptionPane
							.showMessageDialog(
									parent,
									"You don't have any wrong answers! Why would you want to save them to a test?",
									"VTP5", JOptionPane.INFORMATION_MESSAGE);
				} else {
					int answer = wrongAnswersTest.showSaveDialog(parent);
					if (answer == JFileChooser.APPROVE_OPTION) {
						String filePath = wrongAnswersTest.getSelectedFile()
								.getAbsolutePath();

						if (!filePath.endsWith(".txt")) {
							filePath = filePath + ".txt";
						}

						PrintWriter writer = null;

						try {
							writer = new PrintWriter(filePath, "ISO8859_1");

							for (int i = 0; i < parent.getTest()
									.getIncorrectCards().size(); i++) {
								Card card = parent.getTest()
										.getIncorrectCards().get(i);
								writer.println(card.getLangFromPrompt());
								writer.println(card.getLangToPrompt());
							}

							JOptionPane
									.showMessageDialog(
											parent,
											"Success! Your wrong answers have been saved to the following file:\n\n"
													+ filePath
													+ "\n\nTo do a test with only these questions, click \"Import Test File\",\nclick the \"Text File\" button and then select the file you've just saved.",
											"VTP5",
											JOptionPane.INFORMATION_MESSAGE);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							JOptionPane
									.showMessageDialog(
											parent,
											"The following error occurred:\n\n"
													+ e.toString()
													+ "\n\nThat's really sad :(. This could be because VTP5 can't find or access the file you selected.\nPlease report the problem if it keeps happening.",
											"VTP5", JOptionPane.ERROR_MESSAGE);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							JOptionPane
									.showMessageDialog(
											parent,
											"The following error occurred:\n\n"
													+ e.toString()
													+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
											"VTP5", JOptionPane.ERROR_MESSAGE);
						} finally {
							writer.close();
						}
					}

				}
			}
		});

		screenshotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int answer = imagesave.showSaveDialog(getParent());
				if (answer == JFileChooser.APPROVE_OPTION) {
					String filePath = imagesave.getSelectedFile()
							.getAbsolutePath();

					Rectangle screenRect = new Rectangle(Toolkit
							.getDefaultToolkit().getScreenSize());
					try {
						Thread.sleep(1000);

						BufferedImage capture = new Robot()
								.createScreenCapture(screenRect);
						if (!filePath.endsWith(".png")) {
							screenloc = filePath + ".png";
						} else {
							screenloc = filePath;
						}
						ImageIO.write(capture, "png", new File(screenloc));

						SendMail sendMail = new SendMail(test);
						sendMail.m();

					} catch (IOException | AWTException | InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println("screenshot");
				}
			}
		});

		revalidate();
		repaint();
	}

	// DO NOT DELETE THIS METHOD - it makes text-rescaling work!
	public void updatePanel() {
		removeAll();
		test = parent.getTest();
		completedMessage = "You made it! You got "
				+ new BigDecimal(String.valueOf(test.getSuccessRate()))
						.setScale(1, BigDecimal.ROUND_HALF_UP).toString()
				+ "%.";

		if (test.getSuccessRate() == 100) {
			completedMessage = completedMessage + " That's amazing!";
		} else if (test.getSuccessRate() >= 90) {
			completedMessage = completedMessage + " Well done!";
		} else if (test.getSuccessRate() >= 80) {
			completedMessage = completedMessage + " Not too bad!";
		} else if (test.getSuccessRate() >= 70) {
			completedMessage = completedMessage + " Needs some work!";
		} else {
			completedMessage = completedMessage + " Ouch!";
		}

		// completedMessage += "</html>";

		statsList.setVisibleRowCount(4);
		statsList.setForeground(parent.getTextColour());// changes text colour
		Object[] stats = test.getStats();
		statsListModel.removeAllElements();
		statsListModel.addElement("<html><u>Statistics:</u></html>");
		statsListModel.addElement("Total number of words: "
				+ ((int) stats[0] - test.getCards().size()));
		statsListModel.addElement("Answered incorrectly: "
				+ parent.getTest().getIncorrectCards().size());
		statsListModel.addElement("Total number of guesses: " + stats[2]);

		showListLabel.setLineWrap(true);
		completedLabel.setLineWrap(true);

		showListLabel
				.setText("<html>Here's a list of the words you got wrong the first time:</html>");
		completedLabel.setText(completedMessage);

		List<SortKey> sortKeys = new ArrayList<SortKey>();
		sortKeys.add(new SortKey(2, SortOrder.DESCENDING));
		watm = new WrongAnswersTableModel(parent.getTest().getIncorrectCards());
		table = new WebTable(watm);
		table.setEnabled(false);
		table.setRowHeight(table.getFont().getSize() + 10);
		sorter = new TableRowSorter<AbstractTableModel>(watm);
		sorter.setSortKeys(sortKeys);
		table.setRowSorter(sorter);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		sorter.sort();

		saveTest.setText("Create New Test File With These Words");
		restartTest.setText("Start Test Again");

		add(completedLabel, "grow");
		add(statsScrollPane, "grow, spany 2, align right, width 35%!, wrap");
		add(showListLabel, "grow, wrap");
		add(new WebScrollPane(table), "grow, push");
		add(new WebScrollPane(leaderboards), "grow, wrap");
		add(saveTest, "grow, span, split 3");
		add(restartTest, "grow");
		add(screenshotButton, "grow");

		revalidate();
		repaint();
	}

	void setTextColour(Color text) {
		completedLabel.setForeground(text);
		statsList.setForeground(text);
		showListLabel.setForeground(text);
		table.setForeground(text);
		table.getTableHeader().setForeground(text);
		leaderboards.setForeground(text);
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
}

class WrongAnswersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private ArrayList<Card> wrongAnswers;

	public WrongAnswersTableModel(ArrayList<Card> wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	@Override
	public int getColumnCount() {
		return 3;
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
		case 2:
			name = "Times Incorrect";
		}

		return name;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Card card = wrongAnswers.get(rowIndex);
		String value = "";

		switch (columnIndex) {
		case 0:
			value = card.getLangFromPrompt();
			break;
		case 1:
			value = card.getLangToPrompt();
			break;
		case 2:
			value = "" + card.getGuessedWrong();
			break;
		}

		return value;
	}
}
