package vmp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Work {

	
	public static void importer(){
		
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
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		Frame.prim.setText("");
		Frame.sec.setText("");
		for(int i = 0; i <Frame.a.size(); i++){
			Frame.prim.append(Frame.a.get(i)+"\n");
		}
		
		for(int i = 0; i <Frame.q.size(); i++){
			Frame.sec.append(Frame.q.get(i)+"\n");
		}
 
	}
	
	
	public static void exporter(){
		
		String n = System.getProperty("line.separator");
		
		String content = "";
		 
		String[] first = Frame.prim.getText().split("\n");
		String[] second = Frame.sec.getText().split("\n");
		
		for(int i = 0; i < first.length; i++){
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
