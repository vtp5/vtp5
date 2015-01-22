package vtp5.gui;

import java.awt.Color;

import com.alee.laf.button.WebButton;

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
			Color disabled = new Color(col.getRed(), col.getGreen(), col.getBlue(), 100);
			setTopBgColor(disabled);
			setTopSelectedBgColor(disabled);
			setBottomBgColor(disabled.brighter());
			setBottomSelectedBgColor(disabled.darker());
		}
	}
}