package vtp5.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import com.alee.laf.button.WebButton;

public class VTP5Button extends WebButton {

    private Color colour = null;
    private Color lightColour = null;
    private Color darkColour = null;

    public VTP5Button(String text, Color bgColour, Color textColour) {
        setText(text);

        this.colour = bgColour;
        this.lightColour = bgColour.brighter();
        this.darkColour = bgColour.darker();
        super.setOpaque(true);
        super.setContentAreaFilled(true);
        super.setFocusPainted(false);
        super.setBorder(BorderFactory.createMatteBorder(getFontSize(), getFontSize(), 
                getFontSize(), getFontSize(), bgColour));
        setTopBgColor(colour);
        setTopSelectedBgColor(colour);
        setBottomBgColor(lightColour);
        setBottomSelectedBgColor(darkColour);
    }
}