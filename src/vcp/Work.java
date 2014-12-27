package vcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Work {

	static ArrayList<String> words = new ArrayList<String>();
	static String name;
	
	public static void importer(){

		String loc = Frame.f.getPath();
		
		String cons = loc.replace(".txt", ".csv");

			BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader(loc));

				while ((sCurrentLine = br.readLine()) != null) {
					//System.out.println(sCurrentLine);
					words.add(sCurrentLine.replace(", ","\\"));
					
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
					
					String out = "\""+words.get(i)+"\"";
					String out2 = "\""+words.get(i+1)+"\"";
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
