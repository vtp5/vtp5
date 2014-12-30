package vtp5.logic;

import java.io.File;

import javax.swing.JOptionPane;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

class SpellCheck {
	// Variables for spell-checker
	private static String dictFile = "jazzy/dict/english.0";
	// private static String phonetFile = "/VTP5/jazzy/dict/phonet.en";
	private static SpellChecker spellCheck = null;

	static {
		// Set up spell-checker
		try {
			SpellDictionary dictionary = new SpellDictionaryHashMap(new File(
					dictFile), null);

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
