package vtp5;

import javax.swing.SwingUtilities;

import vtp5.gui.VTP5;

public class Main {

	// TODO Change each time a release is about to be pushed out.
	// See http://semver.org/ for more info.
	public static final String build = "v0.1.1";
	public static final String version = "Alpha";
	public static final String appVersion = version + " " + build;
	public static final boolean exportingToJar = false;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				new VTP5();
				System.out.println("Boot completed in "
						+ (System.currentTimeMillis() - startTime)
						+ " milliseconds.");
			}
		});
	}
}