package vtp5;

import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.swabunga.spell.engine.EditDistance;

import vtp5.gui.VTP5;

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

public class Main {

	// TODO Change each time a release is about to be pushed out.
	// See http://semver.org/ for more info.
	public static final String build = "v0.3.0";
	public static final String version = "Alpha";
	public static final String appVersion = version + " " + build;
	public static final boolean exportingToJar = true;

	public static void main(String[] args) {
		
		Runnable r = new Update();
        Thread t = new Thread(r);
        t.start();
        
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				try {
					UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
				} catch (Exception e) {
					JOptionPane
							.showMessageDialog(
									null,
									"The following error occurred:\n\n"
											+ e.toString()
											+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
									"VTP5", JOptionPane.ERROR_MESSAGE);
				}
				new VTP5();

				System.out.println(EditDistance.getDistance("vivoviverevixi",
						"vivoverevixi"));

				System.out.println("Boot completed in "
						+ (System.currentTimeMillis() - startTime)
						+ " milliseconds.");
			}
		});
	}
}


class Update implements Runnable{
    public void run(){
        //TODO search for latest update
    	System.out.println("Searching for new update");
    	
    	try {
			GHRepository repo = GitHub.connectAnonymously().getRepository(
					"vtp5/vtp5");
			GHRelease release = repo.listReleases().asList().get(0);

			if (release.getTagName().contains(Main.build)
					|| release.getName().contains(Main.build)) {
			//why do anything
			} else {
				JEditorPane editorPane = new JEditorPane();
				editorPane.setContentType("text/html");
				//editorPane.setFontSize(20);
				//editorPane.setFont(new Font("Segoe Script", 0, 20));
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

				JOptionPane.showMessageDialog(editorPane, "VTP5");
			}
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred:\n\n"
									+ e.toString()
									+ "\n\nYour computer probably isn't connected to the Internet.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
    }
}