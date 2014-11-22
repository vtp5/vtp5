package vtp5.gui;

import java.awt.Component;

// This class was created because there needs to be a way of storing the original font size of the text in the components, otherwise the scaling will be dodgy
class ComponentWithFontData {
	private Component component;
	private int originalFontSize;

	ComponentWithFontData(Component component, int originalFontSize) {
		super();
		this.component = component;
		this.originalFontSize = originalFontSize;
	}

	Component getComponent() {
		return component;
	}

	void setComponent(Component component) {
		this.component = component;
	}

	int getOriginalFontSize() {
		return originalFontSize;
	}

	void setOriginalFontSize(int originalFontSize) {
		this.originalFontSize = originalFontSize;
	}
	
	

}
