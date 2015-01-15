package animstats;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StatsTest extends Thread {
	
	int ticks=0;
	int size = 0;
	// GUI constants
	private static final String TITLE = "StatsTest";
	
	JPanel panel = new JPanel();
	JFrame f = new JFrame();
	BufferStrategy bs;
	Graphics g;
	
	public StatsTest() {
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setTitle(TITLE);
		f.setSize(900,700);
		f.add(panel);
		f.createBufferStrategy(2);
		bs = f.getBufferStrategy();
		tickt();
		start();
	}

	public void run(){
		while(true){
			g = bs.getDrawGraphics();
			paintComponent(g);
			bs.show();
		}
	}
	
	public static void main(String[] args) {
		new StatsTest().start();
	}
	/***Your Code***/
	public void paintComponent(Graphics g){
	/***Your Code***/
		g.setColor(Color.BLACK);
		g.fillRect(0,0,900,700);
		
	    g.setColor(Color.RED);
	    g.fillOval(50,50,size, size);
	}
	
	public void tickt(){
		ActionListener a = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ticks++;
				tick();
				if(ticks >= 60){
					ticks = 0;
				}
				
			}
			
		};
		
		Timer t = new Timer((int)(1000/60), a);
		t.start();
	}
	
	private void tick() {
		size++;
	}
	
}
