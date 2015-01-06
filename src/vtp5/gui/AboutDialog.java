package vtp5.gui;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import vtp5.Main;
import net.miginfocom.swing.MigLayout;

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

	public AboutDialog() {
		setTitle("About VTP5");
		setLayout(new MigLayout("fillx"));
		add(new JLabel(new ImageIcon(getClass().getResource("/images/vtp.png"))),
				"alignx center, aligny top, wrap");
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
		pack();
		setResizable(false);
		setLocationRelativeTo(this);
	}
}
