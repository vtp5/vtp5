package vtp5.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*VTP5 Copyright (C) 2015  Abdel-Rahim Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ThemePuller {

	public static ArrayList<String> buttonColour = new ArrayList<String>();
	public static ArrayList<String> buttontextColour = new ArrayList<String>();
	public static ArrayList<String> textColour = new ArrayList<String>();
	public static ArrayList<String> backgroundColour = new ArrayList<String>();
	public static ArrayList<String> name = new ArrayList<String>();

	static URL url = null;

	public static void pull() throws UnknownHostException {

		Document doc = null;
		try {
			url = new URL("http://vtp5.github.io/themes/index.html");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			doc = Jsoup.parse(url, 3000);

			// Theme(button, buttontext, text, background, name)

			// int i = 0;
			Element table = doc.select("table").first();
			Iterator<Element> iterator = table.select("td").iterator();
			while (iterator.hasNext()) {
				buttonColour.add(iterator.next().text());
				// System.out.println(buttonColour.get(i));
				buttontextColour.add(iterator.next().text());
				// System.out.println(buttontextColour.get(i));
				textColour.add(iterator.next().text());
				// System.out.println(textColour.get(i));
				backgroundColour.add(iterator.next().text());
				// System.out.println(backgroundColour.get(i));
				name.add(iterator.next().text());
				// System.out.println(name.get(i));
				// i++;
			}
			save();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			offline();
		}

	}

	public static void save() {

		File file = new File("themes.html");
		System.out.println("Hello");
		String content = null;
		try {
			content = Jsoup.connect(url.toString()).get().html();
		} catch (IOException e1) {
			e1.printStackTrace();
			// nofile();

			// TODO Auto-generated catch block
		}

		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void offline() {

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("themes.html"));

			String html = "";

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				html = html + sCurrentLine;
			}

			Document doc = null;
			doc = Jsoup.parse(html);

			// Theme(button, buttontext, text, background, name)

			// int i = 0;
			Element table = doc.select("table").first();
			Iterator<Element> iterator = table.select("td").iterator();
			while (iterator.hasNext()) {
				buttonColour.add(iterator.next().text());
				// System.out.println(buttonColour.get(i));
				buttontextColour.add(iterator.next().text());
				// System.out.println(buttontextColour.get(i));
				textColour.add(iterator.next().text());
				// System.out.println(textColour.get(i));
				backgroundColour.add(iterator.next().text());
				// System.out.println(backgroundColour.get(i));
				name.add(iterator.next().text());
				// System.out.println(name.get(i));
				// i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
			nofile();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void nofile() {

		buttonColour.add("0x663399");
		buttontextColour.add("0xFFFFFF");
		textColour.add("0x000000");
		backgroundColour.add("0xEDEDED");
		name.add("Imperial Purple");

	}

}
