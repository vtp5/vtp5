package vcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
				words.add(sCurrentLine.replace(", ","/"));
				
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