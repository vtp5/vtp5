package vtp5.gui;

import java.awt.Color;

import com.alee.laf.button.WebButton;

public class VTP5Button extends WebButton {

    private Color colour = null;
    private Color lightColour = null;
    private Color darkColour = null;

    public VTP5Button(String text, Color bgColour, Color textColour) {
        setText(text);
        setBackground(bgColour);
        setForeground(textColour);
    }
    
    @Override
    public void setBackground(Color col) {
    	 this.colour = col;
         this.lightColour = col.brighter();
         this.darkColour = col.darker();
         setTopBgColor(colour);
         setTopSelectedBgColor(colour);
         setBottomBgColor(lightColour);
         setBottomSelectedBgColor(darkColour);
    }
 }