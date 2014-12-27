package vcp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

public class Frame extends JFrame {

	private ImageIcon logo = new ImageIcon("res/images/vcp.png");
	
	static File f;
	public static JTextPane textPane;
	JButton chooser,exporter;
	
	private JFileChooser importChooser = new JFileChooser();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setIconImage(logo.getImage());
		importChooser.setFileFilter(new FileNameExtensionFilter(
				"Text Files (*.txt)", "txt"));
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(100, 125);
		getContentPane().setLayout(new MigLayout("", "[grow][][][][][][][][][][][grow]", "[][][grow][grow][][][][][][][grow]"));
		
		
		
		chooser = new JButton("Choose File");	
		getContentPane().add(chooser, "cell 0 0");
		chooser.addActionListener(new EventListener());
		
		textPane = new JTextPane();
		getContentPane().add(textPane, "cell 0 2,grow");
		textPane.setEditable(false);
		
		

	}

	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == chooser) {
				textPane.setText("");
				
				showChooserDialog();
		}
	}
	
	private void showChooserDialog() {
			int selected = importChooser.showOpenDialog(getParent());
			if (selected == JFileChooser.APPROVE_OPTION) {
				f = importChooser.getSelectedFile();
				vcp.Work.importer();
			}

		
		}
	}
	
}
