package vtp5.gui;

import java.awt.Component;

public class Thread2 extends VTP5 implements Runnable{

	@Override
	public void run() {
		for (ComponentWithFontData c : componentList) {
			Component component = c.getComponent();
			
			
			newFontSize = (int) ((double) c.getOriginalFontSize() * scaler);

			// Printlns for debugging:
			// System.out.println("newFontSize: " + newFontSize);

			setFontSize(component, newFontSize);
			FrameListener.frame.revalidate();
			FrameListener.frame.repaint();
		}
		
	}

}
