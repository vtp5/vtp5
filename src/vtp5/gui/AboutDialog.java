package vtp5.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

import net.miginfocom.swing.MigLayout;
import vtp5.Main;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebDialog;

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
public class AboutDialog extends WebDialog {

	private static final long serialVersionUID = 8305515388027804491L;

	private WebLabel vtp5Label = new WebLabel("Vocabulary Testing Program 5, "
			+ Main.appVersion);
	private WebLabel devLabel = new WebLabel(
			"Developed by Abdel Abdalla, Minghua Yin,");
	private WebLabel dev2Label = new WebLabel("Yousuf Ahmed and Nikunj Paliwal.");
	private WebLabel wikiLabel = new HyperlinkLabel("Wiki",
			"https://github.com/vtp5/vtp5/wiki");
	private WebLabel srccodeLabel = new HyperlinkLabel("Source Code",
			"https://github.com/vtp5/vtp5");
	private WebLabel separatorLabel = new WebLabel(
			"------------------------------------------------------------------");
	private WebLabel license1aLabel = new WebLabel(
			"VTP5 Copyright (C) 2015 Abdel-Rahim Abdalla, Minghua Yin,");
	private WebLabel license1bLabel = new WebLabel(
			"Yousuf Mohamed-Ahmed and Nikunj Paliwal");
	private WebLabel license2Label = new WebLabel(
			"VTP5 is licensed under the GNU General Public License (Version 3).");
	private WebLabel license3Label = new HyperlinkLabel(
			"Click here for more information",
			"https://github.com/vtp5/vtp5/wiki/Licensing");
	private WebLabel separatorLabel2 = new WebLabel(
			"------------------------------------------------------------------");
	private WebLabel license4Label = new WebLabel(
			"Jazzy, the spell-checking library used in VTP5,");
	private WebLabel license5Label = new WebLabel("is licensed under the LGPL.");
	private WebButton giveFeedback = new WebButton("Give Feedback");

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
									"https://docs.google.com/forms/d/1TK5I8IXvcCES6Xk1yRAZPRAreRLo9SWebm2o_vx6MA4hY/viewform?usp=send_form"));
				} catch (URISyntaxException | IOException e1) {
					e1.printStackTrace();
					WebOptionPane
							.showMessageDialog(
									null,
									"The following error occurred:\n\n"
											+ e1.toString()
											+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
									"VTP5", WebOptionPane.ERROR_MESSAGE);
				}
			}
		});

		add(new WebLabel(new ImageIcon(getClass().getResource(
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
