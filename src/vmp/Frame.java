package vmp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

public class Frame extends JFrame {

	static ArrayList<String> q = new ArrayList<String>();
	static ArrayList<String> a = new ArrayList<String>();

	private JFileChooser importChooser = new JFileChooser();
	private JFileChooser exportChooser = new JFileChooser();

	static File f;

	JButton in, ex;
	public static JTextArea prim, sec;

	private JPanel contentPane;
	
	
	private ImageIcon logo = new ImageIcon("res/images/vmp.png");

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
		
		exportChooser.setFileFilter(new FileNameExtensionFilter(
				"Text Files (*.txt)", "txt"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][][grow]",
				"[][][][][grow][grow][][][][grow][grow][grow]"));

		in = new JButton("Import File");
		contentPane.add(in, "cell 0 0");
		in.addActionListener(new EventListener());

		ex = new JButton("Export File");
		contentPane.add(ex, "cell 2 0");
		ex.addActionListener(new EventListener());

		JLabel label1 = new JLabel("First Language");
		contentPane.add(label1, "cell 0 1 1 2,alignx left");

		JLabel label2 = new JLabel("Second Language");
		contentPane.add(label2, "cell 2 1 1 2,alignx left");

		prim = new JTextArea();
		JScrollPane sp1 = new JScrollPane(prim);
		contentPane.add(sp1, "cell 0 3 1 9,grow");
		prim.setText("Hello");

		sec = new JTextArea();
		JScrollPane sp2 = new JScrollPane(sec);
		contentPane.add(sp2, "cell 2 3 1 9,grow");
		sec.setText("Hola");

		JSeparator separator = new JSeparator();
		contentPane.add(separator, "cell 1 7");

	}

	private class EventListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == in) {
				System.out.println("in");
				showChooserDialog(0);
			} else if (e.getSource() == ex) {
				System.out.println("ex");
				showChooserDialog(1);
			}
		}

		private void showChooserDialog(int i) {
			if (i == 0) {
				int selected = importChooser.showOpenDialog(getParent());
				if (selected == JFileChooser.APPROVE_OPTION) {
					f = importChooser.getSelectedFile();
					Work.importer();
				}

			} else if (i == 1) {
				int selected = exportChooser.showSaveDialog(getParent());
				if (selected == JFileChooser.APPROVE_OPTION) {
					// TODO save file here
					//
					f = exportChooser.getSelectedFile();
					 Work.exporter();
				}
			}
		}
	}
}
