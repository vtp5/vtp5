package vtp5.gui;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

public class QuestionsDialog extends JPanel{
	public JSlider slider;
	public JSpinner spinner;
	CustomFont cf = new CustomFont();
	public QuestionsDialog(VTP5 obj) {
		setLayout(new MigLayout("fillx"));
		slider = new JSlider();
		spinner = new JSpinner(new SpinnerNumberModel(obj.getTest().getCards().size(), 0,obj.getTest().getCards().size(), 1));
		cf.setFont(spinner, 25);
		slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				spinner.setValue(slider.getValue());			
		}
			
		});
		
		spinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			slider.setValue((int) spinner.getValue());	
			}
			
		});
		add(slider, "alignx center");
		add(spinner, "alignx");
		
	}

}
