package vtp5.gui;

import java.awt.Color;

import com.alee.laf.button.WebButton;

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
public class VTP5Button extends WebButton {

	private Color colour = null;
	private Color lightColour = null;
	private Color darkColour = null;

	public VTP5Button(String text, VTP5 parent) {
		setText(text);
		parent.buttonList.add(this);
	}

	@Override
	public void setBackground(Color col) {
		if (this.isEnabled()) {
			this.colour = col;
			this.lightColour = col.brighter();
			this.darkColour = col.darker();
			setTopBgColor(colour);
			setTopSelectedBgColor(colour);
			setBottomBgColor(lightColour);
			setBottomSelectedBgColor(darkColour);
		} else {
			Color disabled = new Color(col.getRed(), col.getGreen(),
					col.getBlue(), 100);
			setTopBgColor(disabled);
			setTopSelectedBgColor(disabled);
			setBottomBgColor(disabled.brighter());
			setBottomSelectedBgColor(disabled.darker());
		}
	}
}