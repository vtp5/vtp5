package vtp5.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import vtp5.Main;

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
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 8305515388027804491L;

	private JLabel vtp5Label = new JLabel("Vocabulary Testing Program 5, "
			+ Main.appVersion);
	private JLabel devLabel = new JLabel(
			"Developed by Abdel Abdalla, Minghua Yin,");
	private JLabel dev2Label = new JLabel("Yousuf Ahmed and Nikunj Paliwal.");
	private JLabel wikiLabel = new HyperlinkLabel("Wiki",
			"https://github.com/duckifyz/VTP5/wiki");
	private JLabel srccodeLabel = new HyperlinkLabel("Source Code",
			"https://github.com/duckifyz/VTP5");
	private JLabel separatorLabel = new JLabel(
			"------------------------------------------------------------------");
	private JLabel license1aLabel = new JLabel(
			"VTP5 Copyright (C) 2015 Abdel-Rahim Abdalla, Minghua Yin,");
	private JLabel license1bLabel = new JLabel(
			"Yousuf Mohamed-Ahmed and Nikunj Paliwal");
	private JLabel license2Label = new JLabel(
			"VTP5 is licensed under the GNU General Public License (Version 3).");
	private JLabel license3Label = new HyperlinkLabel(
			"Click here for more information",
			"https://github.com/duckifyz/VTP5/wiki/Licensing");
	private JLabel separatorLabel2 = new JLabel(
			"------------------------------------------------------------------");
	private JLabel license4Label = new JLabel(
			"Jazzy, the spell-checking library used in VTP5,");
	private JLabel license5Label = new JLabel("is licensed under the LGPL.");
	private JButton giveFeedback = new JButton("Give Feedback");

	public AboutDialog() {
		setTitle("About VTP5");
		setLayout(new MigLayout("fillx"));

		giveFeedback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					java.awt.Desktop
							.getDesktop()
							.browse(new URI(
									"https://docs.google.com/forms/d/1TK5I8IXvcCES6Xk1yRAZPRAreRLo9Sjm2o_vx6MA4hY/viewform?usp=send_form"));
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
			}
		});

		add(new JLabel(new ImageIcon(getClass().getResource(
				"/images/vtpsmall.png"))), "alignx center, aligny top, wrap");
		add(vtp5Label, "alignx center, wrap");
		add(devLabel, "alignx center, wrap");
		add(dev2Label, "alignx center, wrap");
		add(wikiLabel, "alignx center, wrap");
		add(srccodeLabel, "alignx center, wrap");
		add(separatorLabel, "alignx center, wrap");
		add(license1aLabel, "alignx center, wrap");
		add(license1bLabel, "alignx center, wrap");
		add(license2Label, "alignx center, wrap");
		add(license3Label, "alignx center, wrap");
		add(separatorLabel2, "alignx center, wrap");
		add(license4Label, "alignx center, wrap");
		add(license5Label, "alignx center, wrap");
		add(giveFeedback, "alignx center, wrap");
		pack();
		setResizable(false);
		setLocationRelativeTo(this);
	}
}
