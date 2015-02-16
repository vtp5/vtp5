package vtp5.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

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
public class CustomFont {

	public CustomFont() {

	}

	public void setFont(Component c, int fontSize) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, getClass()
					.getResourceAsStream("/fonts/ubuntu/Ubuntu-C.ttf"));
			font = font.deriveFont((float) fontSize);
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.registerFont(font);
			c.setFont(font);

		} catch (FontFormatException | IOException e) {
			new Font("Arial", Font.PLAIN, fontSize);
		}
	}

	public void setFont(Graphics g, int fontSize) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, getClass()
					.getResourceAsStream("/fonts/ubuntu/Ubuntu-C.ttf"));
			font = font.deriveFont((float) fontSize);
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.registerFont(font);
			g.setFont(font);

		} catch (FontFormatException | IOException e) {
			new Font("Arial", Font.PLAIN, fontSize);
		}
	}
}
