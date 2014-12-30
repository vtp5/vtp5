package vtp_launcher;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;

public class Launcher extends JFrame {

	JButton vtp,vmp,vcp;
	static JTextPane tp;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
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
	public Launcher() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(280, 300);
		setResizable(false);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][][][][][][][][][][][][][][]", "[][][][][][grow]"));
		
		BufferedImage vtpp = null;
		try {vtpp = ImageIO.read(new File("res/images/vtpsmall.png"));} catch (IOException e) {e.printStackTrace();}
		vtp = new JButton(new ImageIcon(vtpp));
		vtp.setBorder(BorderFactory.createEmptyBorder());
		vtp.setContentAreaFilled(false);
		vtp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tp.setText("vtp");
			}
		});
		getContentPane().add(vtp, "cell 8 2");
		
		
		BufferedImage vmpp = null;
		try {vmpp = ImageIO.read(new File("res/images/vmpsmall.png"));} catch (IOException e) {e.printStackTrace();}
		vmp = new JButton(new ImageIcon(vmpp));
		vmp.setBorder(BorderFactory.createEmptyBorder());
		vmp.setContentAreaFilled(false);
		vmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tp.setText("vmp");
			}
		});
		getContentPane().add(vmp, "cell 4 4");
		

		BufferedImage vcpp = null;
		try {vcpp = ImageIO.read(new File("res/images/vcpsmall.png"));} catch (IOException e) {e.printStackTrace();}
		vcp = new JButton(new ImageIcon(vcpp));
		vcp.setBorder(BorderFactory.createEmptyBorder());
		vcp.setContentAreaFilled(false);
		vcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tp.setText("vcp");
			}
		});
		getContentPane().add(vcp, "cell 12 4");
		
		tp = new JTextPane();
		getContentPane().add(tp, "cell 0 5 16 1,grow");
		tp.setEditable(false);
		

		Initial.start();
	}

}
