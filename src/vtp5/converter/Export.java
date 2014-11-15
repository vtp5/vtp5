package vtp5.converter;

import java.io.FileWriter;
import java.io.IOException;

public class Export {

	public static void gen(String file) {
		try {
			FileWriter writer = new FileWriter(file);

			writer.append("1");
			writer.append(',');
			writer.append("2");
			writer.append('\n');

			writer.append("hello");
			writer.append(',');
			writer.append("salve");
			writer.append('\n');


			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
