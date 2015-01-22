package vtp5.gui;

import java.awt.Color;

import com.alee.laf.button.WebButton;

public class VTP5Button extends WebButton {

	private Color colour = null;
	private Color lightColour = null;
	private Color darkColour = null;

	public VTP5Button(String text) {
		setText(text);
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
			setTopBgColor(Color.LIGHT_GRAY);
			setTopSelectedBgColor(Color.LIGHT_GRAY);
			setBottomBgColor(Color.LIGHT_GRAY.brighter());
			setBottomSelectedBgColor(Color.LIGHT_GRAY.darker());
		}
	}
}