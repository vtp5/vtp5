package vtp5.gui;

import java.awt.Color;

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
public class Theme {
	private Color buttonColour;
	private Color buttonTextColour;
	private Color textColour;
	private Color backgroundColour;
	private String name;

	public Theme(int buttonColour, int buttonTextColour, int textColour,
			int backgroundColour, String name) {
		this.buttonColour = new Color(buttonColour);
		this.buttonTextColour = new Color(buttonTextColour);
		this.textColour = new Color(textColour);
		this.backgroundColour = new Color(backgroundColour);
		this.name = name;
	}

	public Theme(Color buttonColour, Color buttonTextColour, Color textColour,
			Color backgroundColour, String name) {
		this.buttonColour = buttonColour;
		this.buttonTextColour = buttonTextColour;
		this.textColour = textColour;
		this.backgroundColour = backgroundColour;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public Color getButtonColour() {
		return buttonColour;
	}

	public void setButtonColour(Color buttonColour) {
		this.buttonColour = buttonColour;
	}

	public Color getButtonTextColour() {
		return buttonTextColour;
	}

	public void setButtonTextColour(Color buttonTextColour) {
		this.buttonTextColour = buttonTextColour;
	}

	public Color getTextColour() {
		return textColour;
	}

	public void setTextColour(Color textColour) {
		this.textColour = textColour;
	}

	public Color getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(Color backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}