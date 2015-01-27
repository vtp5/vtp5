package vtp5.gui;

import java.awt.Desktop;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import vtp5.Main;

class UpdateChecker implements Runnable {

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
				// editorPane.setFontSize(20);
				// editorPane.setFont(new Font("Segoe Script", 0, 20));
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

				JOptionPane.showMessageDialog(null, editorPane, "VTP5",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred while trying to check for updates:\n\n"
									+ e.toString()
									+ "\n\nYour computer probably isn't connected to the Internet.\nDon't worry, everything's fine! :)",
							"VTP5", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}