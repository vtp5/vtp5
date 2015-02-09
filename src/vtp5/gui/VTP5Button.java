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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color lightColour = null;
	private Color darkColour = null;
	private transient VTP5 vtp;

	public VTP5Button(String text, VTP5 parent, boolean enabled) {
		vtp = parent;
		setText(text);
		parent.getButtonList().add(this);
		this.lightColour = vtp.getSelectedTheme().getButtonColour().brighter()
				.brighter();
		this.darkColour = vtp.getSelectedTheme().getButtonColour().darker()
				.darker();
		updateColour();
		if (!enabled) {
			setButtonEnabled(false);
		}
	}

	public void setButtonEnabled(boolean enabledness) {
		if (enabledness) {
			// this.lightColour = vtp.buttonColour.brighter().brighter();
			// this.darkColour = vtp.buttonColour.darker().darker();
			System.out.println(vtp.getSelectedTheme().getButtonColour());
			updateColour();
			setEnabled(true);
		} else {
			setEnabled(false);
			// setForeground(Color.WHITE);
			updateColour();
		}
	}

	private void updateColour() {
		setTopBgColor(lightColour);
		setTopSelectedBgColor(lightColour);
		setBottomBgColor(darkColour);
		setBottomSelectedBgColor(darkColour);
		setRolloverShine(true);
		setDrawFocus(true);
		setDrawShade(true);
		setShineColor(lightColour.brighter().brighter());
		setAnimate(true);
		setRolloverShadeOnly(true);
		setRound(10);
		revalidate();
		repaint();
	}

	@Override
	public void setBackground(Color col) {
		this.lightColour = col.brighter().brighter();
		this.darkColour = col.darker().darker();
		updateColour();
	}

}