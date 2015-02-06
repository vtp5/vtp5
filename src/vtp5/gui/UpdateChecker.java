package vtp5.gui;

import java.awt.Desktop;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

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
class UpdateChecker implements Runnable {
	private JFrame parent;

	UpdateChecker(JFrame parent) {
		this.parent = parent;
	}

	public void run() {
		// TODO search for latest update
		System.out.println("Searching for new update");

		try {
			GHRepository repo = GitHub.connectAnonymously().getRepository(
					"vtp5/vtp5");
			GHRelease release = repo.listReleases().asList().get(0);

			if (release.getTagName().contains(Main.build)
					|| release.getName().contains(Main.build)) {
				// why do anything
			} else {
				JEditorPane editorPane = new JEditorPane();
				editorPane.setContentType("text/html");
				editorPane
						.setText("You are not running the latest release of VTP5. \n\nThe latest version, "
								+ release.getName()
								+ ", can be found "
								+ "<a href='https://github.com/vtp5/vtp5/releases'>here</a>.");

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

				JOptionPane.showMessageDialog(parent, editorPane, "VTP5",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}