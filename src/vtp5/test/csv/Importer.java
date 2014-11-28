package vtp5.test.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Importer {

	
	static ArrayList<String> q = new ArrayList<String>();
	static ArrayList<String> a = new ArrayList<String>();
	
	static int i = -1;
	
	public static void main(String[] args) {

		String csvFile = "res/files/test.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				i++;
				// use comma as separator
				String[] word = line.split(cvsSplitBy);
				
				q.add(word[0].replace("\"",""));
				a.add(word[1].replace("\"",""));
				
				
				

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Test.test();
		//System.out.println("Done");
	}

}
