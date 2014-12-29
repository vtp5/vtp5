package vtp5.test.txt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import vtp5.logic.TestFile;

/*VTP5 Copyright (C) 2014-2015  Abdel-Rahim Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

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
public class Importer {

	static ArrayList<String> q = new ArrayList<String>();
	static ArrayList<String> a = new ArrayList<String>();
	@SuppressWarnings("unused")
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
