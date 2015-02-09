package vtp5.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

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
public class SpellCheck {
	// Variables for spell-checker
	private static File dictFile;
	private static SpellChecker spellCheck = null;

	public static void loadSpellChecker() {
		// Set up spell-checker
		try {

			dictFile = streamToFile(SpellCheck.class
					.getResourceAsStream("/dict/english.0"));

			SpellDictionary dictionary = new SpellDictionaryHashMap(dictFile,
					null);

			spellCheck = new SpellChecker(dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(
							null,
							"The following error occurred:\n\n"
									+ e.toString()
									+ "\n\nThat's really sad :(. It means that the spell-checker hasn't loaded properly.\nHowever, you'll still be able to complete your test - as long as you HAVEN'T enabled \"experimental settings\".\n\nPlease report the problem if it keeps happening.",
							"VTP5", JOptionPane.ERROR_MESSAGE);
		}
	}

	static boolean containsSpellingErrors(String answer) {
		int result = spellCheck.checkSpelling(new StringWordTokenizer(answer));
		if (result != SpellChecker.SPELLCHECK_OK) {
			return true;
		} else {
			return false;
		}
	}

	private static File streamToFile(InputStream in) throws IOException {
		final File tempFile = File.createTempFile("vtp5_temp_file", ".temp");
		tempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return tempFile;
	}
}
