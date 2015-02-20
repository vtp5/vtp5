package vtp5.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ThemePuller {

	public static ArrayList<String> buttonColour = new ArrayList<String>();
	public static ArrayList<String> buttontextColour = new ArrayList<String>();
	public static ArrayList<String> textColour = new ArrayList<String>();
	public static ArrayList<String> backgroundColour = new ArrayList<String>();
	public static ArrayList<String> name = new ArrayList<String>();
	
	public static void pull(){
		
		URL url = null;
		try {
			url = new URL("http://vtp5.github.io/themes/index.html");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
            System.out.println(buttonColour.get(i));
            buttontextColour.add(iterator.next().text());
            System.out.println(buttontextColour.get(i));
            textColour.add(iterator.next().text());
            System.out.println(textColour.get(i));
            backgroundColour.add(iterator.next().text());
            System.out.println(backgroundColour.get(i));
            name.add(iterator.next().text());
            System.out.println(name.get(i));
            i++;
        }
		
		
		
	}
	
}
