package vtp5.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class CustomFont {
	
	public CustomFont() {
		
	}
	public void setFont(Component c, int fontSize){
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
			/*JOptionPane.showMessageDialog(this, "The font file was not found.",
					"VTP5", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();*/

			// Use Arial font (because pretty much everyone has it)
			new Font("Arial", Font.PLAIN, fontSize);
		}
	}

}
