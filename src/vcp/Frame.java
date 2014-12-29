package vcp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

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
public class Frame extends JFrame {

	private ImageIcon logo = new ImageIcon("res/images/vcp.png");

	static File f;
	public static JTextPane textPane;
	JButton chooser, exporter;

	private JFileChooser importChooser = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setIconImage(logo.getImage());
		importChooser.setFileFilter(new FileNameExtensionFilter(
				"Text Files (*.txt)", "txt"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(100, 125);
		getContentPane().setLayout(
				new MigLayout("", "[grow][][][][][][][][][][][grow]",
						"[][][grow][grow][][][][][][][grow]"));

		chooser = new JButton("Choose File");
		getContentPane().add(chooser, "cell 0 0");
		chooser.addActionListener(new EventListener());

		textPane = new JTextPane();
		getContentPane().add(textPane, "cell 0 2,grow");
		textPane.setEditable(false);

	}

	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == chooser) {
				textPane.setText("");

				showChooserDialog();
			}
		}

		private void showChooserDialog() {
			int selected = importChooser.showOpenDialog(getParent());
			if (selected == JFileChooser.APPROVE_OPTION) {
				f = importChooser.getSelectedFile();
				vcp.Work.importer();
			}

		}
	}

}
