package vtp5.gui;

import javax.swing.JDialog;
import javax.swing.JSlider;

import net.miginfocom.swing.MigLayout;

public class QuestionsDialog extends JDialog{
	private JDialog questionsDialog;
	private JSlider slider;
	public QuestionsDialog() {
		setLayout(new MigLayout("fillx"));
		slider = new JSlider();
		setSize(500,400);
		add(slider, "alignx center, wrap");
		
	}

}
