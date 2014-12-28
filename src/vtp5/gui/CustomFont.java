package vtp5.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.IOException;

/*VTP5 Copyright (C) 2014-2015  Abdel Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

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
public class CustomFont {

	public CustomFont() {

	}

	public void setFont(Component c, int fontSize) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT,
					new FileInputStream("res/fonts/ubuntu/Ubuntu-C.ttf"));
			// font = Font.createFont(Font.TRUETYPE_FONT, Loader
			// .getInputStream("fonts/didactgothic/DidactGothic.ttf"));
			/*
			 * Font font1 = new Font.createFont(Font.TRUETYPE_FONT,
			 * (FileInputStream)Loader.getInputStream(
			 * "res/fonts/didactgothic/DidactGothic.ttf"));
			 */
			font = font.deriveFont((float) fontSize);
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.registerFont(font);
			c.setFont(font);

		} catch (FontFormatException | IOException e) {
			/*
			 * JOptionPane.showMessageDialog(this,
			 * "The font file was not found.", "VTP5",
			 * JOptionPane.ERROR_MESSAGE); e.printStackTrace();
			 */

			// Use Arial font (because pretty much everyone has it)
			new Font("Arial", Font.PLAIN, fontSize);
		}
	}

}
