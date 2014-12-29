package vtp5.gui;

import java.awt.Component;

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
