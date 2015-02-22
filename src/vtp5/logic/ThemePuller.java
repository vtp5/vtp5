package vtp5.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import vtp5.gui.VTP5;

public class ThemePuller {

	public static ArrayList<String> buttonColour = new ArrayList<String>();
	public static ArrayList<String> buttontextColour = new ArrayList<String>();
	public static ArrayList<String> textColour = new ArrayList<String>();
	public static ArrayList<String> backgroundColour = new ArrayList<String>();
	public static ArrayList<String> name = new ArrayList<String>();
	
	static URL url = null;
	
	public static void pull() throws UnknownHostException{
		
		urldecider();
		
		Document doc = null;
		try {
			doc = Jsoup.parse(url, 3000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Theme(button, buttontext, text, background, name)
		
		int i = 0;
        Element table = doc.select("table").first();
        Iterator<Element> iterator = table.select("td").iterator();
        while(iterator.hasNext()){
        	buttonColour.add(iterator.next().text());
            //System.out.println(buttonColour.get(i));
            buttontextColour.add(iterator.next().text());
            //System.out.println(buttontextColour.get(i));
            textColour.add(iterator.next().text());
            //System.out.println(textColour.get(i));
            backgroundColour.add(iterator.next().text());
            //System.out.println(backgroundColour.get(i));
            name.add(iterator.next().text());
            //System.out.println(name.get(i));
            i++;
        }
		save();
	}
	
	public static void save(){
		
		File file = new File(".vtp5/themes.html");
		System.out.println("Hello"); 
		String content = null;
		try {
			content = Jsoup.connect(url.toString()).get().html();
		} catch (IOException e1) {
			e1.printStackTrace();
			//nofile();
			
			// TODO Auto-generated catch block
		}
		
		
		try{
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static void nofile(){
		
		buttonColour.add("0x663399");
		buttontextColour.add("0xFFFFFF");
		textColour.add("0x000000");
		backgroundColour.add("0xEDEDED");
		name.add("Imperial Purple");
		
	}
	
	public static void urldecider(){
		
		try {
			url = new URL("http://vtp5.github.io/themes/index.html");
		    URLConnection myURLConnection = url.openConnection();
		    myURLConnection.connect();
		} catch (IOException e1) {
			e1.printStackTrace();
			
			VTP5 vtp5 = new VTP5();
			
			File path = vtp5.APPDATA_PATH;
			File file = new File(vtp5.APPDATA_PATH + System.getProperty("file.seperator")+"themes.html");
			
			
			try {
				url = new URL(file.toString());
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
}
