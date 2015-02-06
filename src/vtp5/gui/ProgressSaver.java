package vtp5.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import vtp5.logic.TestFile;

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
class ProgressSaver implements Runnable {
	private String filePath;
	private TestFile test;
	private JFrame parent;

	ProgressSaver(String filePath, TestFile test, JFrame parent) {
		super();
		this.filePath = filePath;
		this.test = test;
		this.parent = parent;
	}

	@Override
	public void run() {
		if (!filePath.endsWith(".vtp5")) {
			filePath = filePath + ".vtp5";
		}

		File progressFile = new File(filePath);
		try (ObjectOutputStream output = new ObjectOutputStream(
				new FileOutputStream(progressFile))) {
			output.writeObject(test);
			JOptionPane
					.showMessageDialog(
							parent,
							"Success! Your progress has been saved to the following file:\n\n"
									+ filePath
									+ "\n\nTo carry on with this test later, click \"Import Test File\"\nand then click the \"Progress File\" button.",
							"VTP5", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane
					.showMessageDialog(
							parent,
							"The following error occurred:\n\n"
									+ e1.toString()
									+ "\n\nThat's really sad :(. Please report the problem if it keeps happening.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
		}
	}

}
