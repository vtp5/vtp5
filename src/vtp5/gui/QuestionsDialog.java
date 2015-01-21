package vtp5.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

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
public class QuestionsDialog extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JSlider slider;
	private JSpinner spinner;

	private JLabel questionsDialog = new JLabel(
			"How many questions do you want?");
	CustomFont cf = new CustomFont();

	public QuestionsDialog(VTP5 obj) {
		setLayout(new MigLayout("fillx"));
		slider = new JSlider();
		spinner = new JSpinner(new SpinnerNumberModel(obj.getTest().getCards()
				.size(), 1, obj.getTest().getCards().size(), 1));

		cf.setFont(spinner, 25);
		cf.setFont(slider, 25);
		cf.setFont(questionsDialog, 25);

		slider.setMinimum(1);
		slider.setMaximum(obj.getTest().getCards().size());
		slider.setValue(obj.getTest().getCards().size());
		slider.setMajorTickSpacing(Math
				.round(obj.getTest().getCards().size() / 4));
		slider.setPaintTicks(true);

		spinner.setValue(obj.getTest().getCards().size());

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				spinner.setValue(slider.getValue());
			}

		});

		spinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				slider.setValue((int) spinner.getValue());
			}

		});
		add(questionsDialog, "alignx center, span, grow, push, wrap");
		add(slider, "alignx center");
		add(spinner, "alignx center");

	}

	public JSlider getSlider() {
		return slider;
	}

	public JSpinner getSpinner() {
		return spinner;
	}

}
