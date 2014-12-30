package vtp_launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
		
		//String vtpdir = p[0]+"\\VTP5_files\\vtp5";
		String vtpdir ="E:\\VTP5_files\\vtp5";
		
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
		       System.out.println(vtpdir);  
		       
		     }
		  }else{
			  System.out.println("Already there");
			  
			  File fi = new File(vtpdir + "\\" + vtpversion + ".jar");
			  if(fi.exists()) {
				  System.out.println("Latest build present");
			  }
			  else{
				//copyFolder(srcFolder,theDir);
				  File srcFolder = new File("http://192.168.1.141/vtp5/"  /*"http://sabr.ddns.net/vtp5"*/);
			  }
			  
		  }
		
	}
	
	public static void parse(){
		
		try {
			Document test = Jsoup.connect("http://en.wikipedia.org/").get();
			
			Document docabdel = Jsoup.connect("http://192.168.1.141").get(); //this is needed for abdel to work on this
			Document doc = Jsoup.connect("http://sabr.ddns.net").get(); //other people
			
			vtpversion = docabdel.title();//change this too
			System.out.println(vtpversion);
			
		} catch (IOException e) {
			System.out.println("Can't connect to internet");
		}
		
	}
	
	public static void copyFolder(File src, File dest)
	    	throws IOException{
	 
	    	if(src.isDirectory()){
	 
	    		//if directory not exists, create it
	    		if(!dest.exists()){
	    		   dest.mkdir();
	    		   System.out.println("Directory copied from " 
	                              + src + "  to " + dest);
	    		}
	 
	    		//list all the directory contents
	    		String files[] = src.list();
	 
	    		for (String file : files) {
	    		   //construct the src and dest file structure
	    		   File srcFile = new File(src, file);
	    		   File destFile = new File(dest, file);
	    		   //recursive copy
	    		   copyFolder(srcFile,destFile);
	    		}
	 
	    	}else{
	    		//if file, then copy it
	    		//Use bytes stream to support all file types
	    		InputStream in = new FileInputStream(src);
	    	        OutputStream out = new FileOutputStream(dest); 
	 
	    	        byte[] buffer = new byte[1024];
	 
	    	        int length;
	    	        //copy the file content in bytes 
	    	        while ((length = in.read(buffer)) > 0){
	    	    	   out.write(buffer, 0, length);
	    	        }
	 
	    	        in.close();
	    	        out.close();
	    	        System.out.println("File copied from " + src + " to " + dest);
	    	}
	    }
}
