package vtp_launcher;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

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
public class Launcher extends JFrame {

	public static String vtpversion;

	JButton vtp, vmp, vcp;
	static JTextArea tp;

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
		getContentPane().setLayout(
				new MigLayout("", "[grow][grow][][][][][][][][][][][][][][]",
						"[][][][][][grow]"));

		BufferedImage vtpp = null;
		try {
			vtpp = ImageIO.read(new File("res/images/vtpsmall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		vtp = new JButton(new ImageIcon(vtpp));
		vtp.setBorder(BorderFactory.createEmptyBorder());
		vtp.setContentAreaFilled(false);
		vtp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tp.setText("Opening VTP5");
				// Run a java app in a separate system process
				Process proc = null;
				try {
					proc = Runtime.getRuntime()
							.exec("java -jar " + Initial.fil);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Then retreive the process output
				InputStream in = proc.getInputStream();
				InputStream err = proc.getErrorStream();
			}
		});
		getContentPane().add(vtp, "cell 8 2");

		BufferedImage vmpp = null;
		try {
			vmpp = ImageIO.read(new File("res/images/vmpsmall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		try {
			vcpp = ImageIO.read(new File("res/images/vcpsmall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		vcp = new JButton(new ImageIcon(vcpp));
		vcp.setBorder(BorderFactory.createEmptyBorder());
		vcp.setContentAreaFilled(false);
		vcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tp.setText("vcp");
			}
		});
		getContentPane().add(vcp, "cell 12 4");

		tp = new JTextArea();
		getContentPane().add(tp, "cell 0 5 16 1,grow");
		tp.setEditable(false);
		Initial.start();
	}

}
