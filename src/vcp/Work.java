package vcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
public class Work {

	static ArrayList<String> words = new ArrayList<String>();
	static String name;

	public static void importer() {

		String loc = Frame.f.getPath();

		String cons = loc.replace(".txt", ".csv");

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(loc));

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				words.add(sCurrentLine.replace(", ", "\\"));

			}

			exporter(cons);

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

	public static void exporter(String file) {
		try {
			FileWriter writer = new FileWriter(file);

			for (int i = 0; i < words.size(); i = i + 2) {

				String out = "\"" + words.get(i) + "\"";
				String out2 = "\"" + words.get(i + 1) + "\"";
				writer.append(out);
				writer.append(',');
				writer.append(out2);
				writer.append('\n');

			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Frame.textPane.setText("completed");

	}

}
