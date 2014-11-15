package vtp5.converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Import {

	// use this to convert old txt files to csv files.
	// we can support both but with csv we can have more complex stuff such as
	// different marks

	ArrayList<String> words = new ArrayList<String>();

	public static void main(String[] args) {

		BufferedReader br = null;

		try {

			
			
			String sCurrentLine;

			br = new BufferedReader(new FileReader("res/to.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

			Export.gen("res/to.csv");
			
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