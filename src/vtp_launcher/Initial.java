package vtp_launcher;

import java.io.File;

public class Initial extends Launcher{

	public static void start(){
		
		File f = new File(System.getProperty("java.class.path"));
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		String p[] = path.split(";");
		
		tp.setText(p[0]);
		
		File theDir = new File(p[0]+"\\VTP5_files");

		 
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
	
}
