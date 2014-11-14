package vtp5.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class VTP5 extends JFrame {
	// TODO Generate the serialVersionUID once class has been finished.
	private JMenu menu;
	private JMenuBar bar;
	private JMenuItem importItem;

	public VTP5() {
		// Sets up JMenuBar.
		bar = new JMenuBar();
		menu = new JMenu("File");
		importItem = new JMenuItem("Import");
		importItem.addActionListener(new MenuItemListener());
		menu.setMnemonic(KeyEvent.VK_F);
		menu.add(importItem);
		bar.add(menu);
		setJMenuBar(bar);

		// Sets JFrame properties.
		setSize(500, 400);
		setTitle("VTP5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private class MenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == importItem) {
				// Will run the open file method(s) here.
			}
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