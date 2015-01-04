package vtp5;

import javax.swing.SwingUtilities;

import vtp5.gui.VTP5;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				new VTP5();
				System.out.println("Boot completed in "
						+ (System.currentTimeMillis() - startTime) + " milliseconds.");
			}
		});
	}
}
