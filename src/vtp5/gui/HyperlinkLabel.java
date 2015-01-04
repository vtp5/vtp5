package vtp5.gui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HyperlinkLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public HyperlinkLabel(String text, final String link) {
		this.setText("<html><a href=\"" + link + "\">" + text + "</a></html>");
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
					JOptionPane
							.showMessageDialog(
									null,
									"The following error occurred:\n\n"
											+ e1.toString()
											+ "\n\nThat's really sad :(. This may have happened because you're not connected to the internet. Please report the problem if it keeps happening.",
									"VTP5", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
