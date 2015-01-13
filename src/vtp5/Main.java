package vtp5;

import javax.swing.SwingUtilities;

import vtp5.gui.VTP5;
import vtp5.util.DingSound;

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
	public static final String build = "v0.1.3";
	public static final String version = "Alpha";
	public static final String appVersion = version + " " + build;
	public static final boolean exportingToJar = false;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				new VTP5();
				DingSound sound = new DingSound();
				sound.play();
				System.out.println("Boot completed in "
						+ (System.currentTimeMillis() - startTime)
						+ " milliseconds.");
			}
		});
	}
}