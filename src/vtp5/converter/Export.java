package vtp5.converter;

import java.io.FileWriter;
import java.io.IOException;

public class Export extends Import {

	public static void gen(String file) {
		try {
			FileWriter writer = new FileWriter(file);

			for (int i = 0; i < words.size(); i = i + 2) {

				writer.append(words.get(i));
				writer.append(',');
				writer.append(words.get(i + 1));
				writer.append('\n');

			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
