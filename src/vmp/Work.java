package vmp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*VTP5 Copyright (C) 2014-2015  Abdel Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

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
public class Work {

	public static void importer() {

		File file = Frame.f;

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(file));

			int i = -1;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);

				i++;
				// System.out.println(sCurrentLine);

				if ((i & 1) == 0) {
					// System.out.println("Even");
					Frame.q.add(sCurrentLine);
				} else {
					// System.out.println("Odd");
					Frame.a.add(sCurrentLine);
				}

			}

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

		Frame.prim.setText("");
		Frame.sec.setText("");
		for (int i = 0; i < Frame.a.size(); i++) {
			Frame.prim.append(Frame.a.get(i) + "\n");
		}

		for (int i = 0; i < Frame.q.size(); i++) {
			Frame.sec.append(Frame.q.get(i) + "\n");
		}

	}

	public static void exporter() {

		String n = System.getProperty("line.separator");

		String content = "";

		String[] first = Frame.prim.getText().split("\n");
		String[] second = Frame.sec.getText().split("\n");

		for (int i = 0; i < first.length; i++) {
			content = content + first[i] + n + second[i] + n;
		}
		try {

			File file = new File(Frame.f + ".txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
