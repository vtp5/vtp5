package notyours.vtp5;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class VTP5 extends JFrame {
	private static final long serialVersionUID = -5105462185201329709L;
	// HEY! - MIng
	private JMenu menu;
	private JMenuBar bar;
	private JMenuItem importItem;
	
	
	public VTP5() {
		bar = new JMenuBar();
		menu = new JMenu("File");
		importItem = new JMenuItem("Import");
		
		menu.setMnemonic(KeyEvent.VK_F);
		menu.add(importItem);
		bar.add(menu);
		setJMenuBar(bar);
		
		
		
		setSize(500, 400);
		setTitle("VTP5");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VTP5();
	}
}