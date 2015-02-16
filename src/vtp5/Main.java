package vtp5;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
	public static final String build = "v0.4.0";
	public static final String version = "Beta";
	public static final String appVersion = version + " " + build;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				long startTime = System.currentTimeMillis();
				try {

					/*
					 * This will return Long.MAX_VALUE if there is no preset
					 * limit
					 */
					long maxMemory = Runtime.getRuntime().maxMemory() / 1000000;
					/* Maximum amount of memory the JVM will attempt to use */
					System.out.println("Maximum memory (MB): "
							+ (maxMemory == Long.MAX_VALUE ? "no limit"
									: maxMemory));

					// TODO change this value after further tests
					if (maxMemory >= 500) {
						System.out.println("yipee weblaf enabled");
						UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
					} else {
						System.out.println("not enough ram");
					}

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

				System.out.println("Boot completed in "
						+ (System.currentTimeMillis() - startTime)
						+ " milliseconds.");
			}
		});
	}
}