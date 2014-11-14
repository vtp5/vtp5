package vtp5.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import vtp5.logic.TestFile;

public class VTP5 extends JFrame {
	// TODO Generate the serialVersionUID once class has been finished.
	
	// GUI components.
	private JMenu menu;
	private JMenuBar bar;
	private JMenuItem importItem;
	private JFileChooser chooser = new JFileChooser();
	
	private FileNameExtensionFilter chooserFilter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
	
	TestFile test;

	public VTP5() {
		// Sets up JMenuBar.
		bar = new JMenuBar();
		menu = new JMenu("File");
		importItem = new JMenuItem("Import");
		importItem.addActionListener(new MenuItemListener());
		menu.setMnemonic(KeyEvent.VK_F);
		importItem.setMnemonic(KeyEvent.VK_I);
		menu.add(importItem);
		bar.add(menu);
		setJMenuBar(bar);

		// Sets up JFileChooser
		chooser.setFileFilter(chooserFilter);		
		
		// Sets JFrame properties.
		setSize(500, 400);
		setTitle("VTP5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private class MenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == importItem) {
				// Open JFileChooser and then creates test file
				int selected = chooser.showOpenDialog(getParent());
				if (selected == JFileChooser.APPROVE_OPTION) {
					// Show confirmation dialog if currently in a test.
					// If so, clear old test.
					test = new TestFile(chooser.getSelectedFile());
				}
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