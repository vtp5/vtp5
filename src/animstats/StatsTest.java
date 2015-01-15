package animstats;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
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
public class StatsTest extends Thread {
	
	//http://www.hardcode.de/jxinput/
	//https://code.google.com/p/tankz/source/browse/src/tankz/test/JXInputTest.java?r=65
	
	int percentageY = 350;
	int leftY = 350;
	int correctY = 350;
	int correctX = 230;
	int incorrectX = 230;
	
	int changeX = 0; 
	int changeY = 0; 
	
	//velocities
	int vely = 5;
	int velx = 0;
	
	int ticks = 0;
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
		f.setSize(500,300);
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
		
		//circles
		
		g.setColor(new Color(102,51,153));
	    g.fillOval(230,leftY,40,40);
	    
	    g.setColor(Color.GREEN);
	    g.fillOval(230,percentageY,40,40);
	    
	    g.setColor(Color.ORANGE);
	    g.fillOval(incorrectX,correctY,40,40);
	    
	    g.setColor(Color.BLUE);
	    g.fillOval(correctX,correctY,40,40);
	    
	    
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
		
		if(leftY>190){
			leftY-=vely;
			percentageY-=vely;
			correctY-=vely;
		}else{
			int c = 4;
			changeX = (int) Math.sqrt((double) c);
			
			if(percentageY>110){
				percentageY-=vely;
			}
		}
		
	}
	
}
