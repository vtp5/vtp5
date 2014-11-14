package duckifyz.vtp5;

import javax.swing.JFrame;

public class VTP5 extends JFrame {
	private static final long serialVersionUID = -5105462185201329709L;
	
	public VTP5() {
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