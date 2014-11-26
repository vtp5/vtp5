package vtp5.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import vtp5.logic.TestFile;

public class Importer {

	static ArrayList<String> q = new ArrayList<String>();
	static ArrayList<String> a = new ArrayList<String>();
	private TestFile file;
	static int i = -1;

	public Importer(TestFile test) {
		this.file = test;
	}

	public static void main(String[] args) {

		BufferedReader br = null;

		String file = "res/files/test.txt";

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(file));

			while ((sCurrentLine = br.readLine()) != null) {
				i++;
				// System.out.println(sCurrentLine);

				if ((i & 1) == 0) {
					// System.out.println("Even");
					q.add(sCurrentLine);
				} else {
					// System.out.println("Odd");
					a.add(sCurrentLine);
				}

			}

			Test.test();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
