package vtp5.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import vtp5.logic.TestFile;

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
