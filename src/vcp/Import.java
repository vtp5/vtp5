package vcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
public class Import {

	// use this to convert old txt files to csv files.
	// we can support both but with csv we can have more complex stuff such as
	// different marks

	static ArrayList<String> words = new ArrayList<String>();

	static String loc = "res/files/test.txt";

	public static void main(String[] args) {

		String[] parts = loc.split("/"); // splits location at slash
		String cons = parts[0] + "/";

		for (int i = 1; i < parts.length - 1; i++) {

			cons = cons + parts[i] + "/"; // builds up location for csv

		}
		System.out.println(cons);
		String a = parts[parts.length - 1];
		String name = a.replace(".txt", ".csv");

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(loc));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				words.add(sCurrentLine.replace(", ", "/"));

			}

			Export.gen(cons + name);

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