package vtp5.gui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import vtp5.logic.Theme;

public class ThemeSelectorModel extends AbstractListModel<Theme> implements ComboBoxModel<Theme>{
	private ArrayList<Theme> themes;
	
	public ThemeSelectorModel(ArrayList<Theme> themes) {
		this.themes = themes;
	}

	@Override
	public Theme getElementAt(int arg0) {
		return themes.get(arg0);
	}

	@Override
	public int getSize() {
		return themes.size();
	}

	@Override
	public void setSelectedItem(Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}
}