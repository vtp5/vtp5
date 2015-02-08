package vtp5.gui;

import java.awt.Color;

public class Theme {
	private Color colour;
	private String name;

	public Theme(int colour, String name) {
		this.colour = new Color(colour);
		this.name = name;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}