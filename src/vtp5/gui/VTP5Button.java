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
		if(this.isEnabled()){
		this.colour = parent.buttonColour;
		this.lightColour = parent.buttonColour.brighter();
		this.darkColour = parent.buttonColour.darker();		
		}else{
			Color disabled = new Color(parent.buttonColour.getRed(), parent.buttonColour.getGreen(),
					parent.buttonColour.getBlue(), 100);
		this.colour = disabled;
		this.lightColour = disabled.brighter();
		this.darkColour = disabled.darker();
		}
	}

	@Override
	public void setBackground(Color col) {
			System.out.println(col);
			setTopBgColor(lightColour);
			setTopSelectedBgColor(colour);
			setBottomBgColor(darkColour);
			setBottomSelectedBgColor(darkColour);
	}
	
}