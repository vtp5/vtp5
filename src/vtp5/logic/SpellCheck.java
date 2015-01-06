package vtp5.logic;

import java.io.File;

import javax.swing.JOptionPane;

import vtp5.Main;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

public class SpellCheck {
	// Variables for spell-checker
	private static String dictFile;
	private static SpellChecker spellCheck = null;

	public static void loadSpellChecker() {
		try {
			SpellDictionary dictionary = new SpellDictionaryHashMap(new File(
					dictFile), null);

			if (Main.exportingToJar) {
				dictFile = "english.0";
			} else {
				dictFile = "jazzy/dict/english.0";
			}

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
}
