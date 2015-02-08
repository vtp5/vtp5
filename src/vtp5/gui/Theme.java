package vtp5.gui;

import java.awt.Color;

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