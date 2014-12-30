package vtp_launcher;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Initial extends Launcher{

	public static void start(){
		parse();
		File f = new File(System.getProperty("java.class.path"));
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		String p[] = path.split(";");
		
		tp.setText(p[0]);
		
		String vtpdir = p[0]+"\\VTP5_files";
		
		File theDir = new File(vtpdir);

		 
		  if (!theDir.exists()) {
		    System.out.println("creating directory: " + "VTP5");
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		     } catch(SecurityException se){
		        
		     }        
		     if(result) {    
		       System.out.println("DIR created");  
		     }
		  }else{
			  System.out.println("Already there");
			  
		  }
		
	}
	
	public static void parse(){
		
		try {
			Document test = Jsoup.connect("http://en.wikipedia.org/").get();
			
			Document docabdel = Jsoup.connect("http://192.168.1.141").get(); //this is needed for abdel to work on this
			Document doc = Jsoup.connect("http://sabr.ddns.net").get(); //other people
			
			vtpversion = docabdel.title();
			System.out.println(vtpversion);
			
		} catch (IOException e) {
			System.out.println("Can't connect to internet");
		}
		
	}
	
}
