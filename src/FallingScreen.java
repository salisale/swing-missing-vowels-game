import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import javax.swing.JPanel;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

public class FallingScreen extends JPanel{
	private CluePanel cluePanel;
	private Integer[] x_pos_arr = {10,20,30,40,100,110,120,130,150,170,210,220,230,240,
							250,280,300}; // default position
	private Integer[] x_pos_arr_b = {20,40,60,80,100,120,140,160}; // left position
	private List<Integer> x_pos = Arrays.asList(x_pos_arr);
	private final int[] SPEED_OPTION = {1000, 600, 400}; 
	private final int[] BREAK_OPTION = {8000, 6000, 4000};
	private int break_period; // drop every x seconds
	private int speed; // speed 1, 2, 3
	private int mode; // 1 = no display, 2 = display
	private ClueTile[] tileArr;
	
	public FallingScreen(CluePanel cluePanel, String[] clues, int speed, int mode) {
		this.cluePanel = cluePanel;
		this.speed = SPEED_OPTION[speed-1]; 
		this.break_period = BREAK_OPTION[speed-1];
		this.mode = mode; 

		setLayout(null);
		setSize(360,500);
		setBackground(Color.WHITE);
		
		init(clues);
	}

	private void init(String[] clues) {
		tileArr = new ClueTile[clues.length];
		Collections.shuffle(x_pos); // randomize clue position
		
		int delay = 1000;
		for (int i=0; i<clues.length; i++) {
			ClueTile tile = new ClueTile(clues[i]);
			tile.setSize(tile.getPreferredSize()); // null layout requires this
			
			// if clue is long, set to lefter position
			Random rnd = new Random();
			int pos = x_pos.get(i)+tile.getWidth()>370? 
					x_pos_arr_b[rnd.nextInt(x_pos_arr_b.length)]: 
					x_pos.get(i);
					
			tile.setBounds(pos, 0, tile.getWidth(), tile.getHeight());
			tile.makeInvisible();
			tileArr[i] = tile;
			add(tileArr[i]);
			dropClueTile(tileArr[i], delay);
			delay+=break_period; 
		}
		
		// if screen is finished, inform cluePanel
		// checking the status every 3 seconds
		Timer tm = new Timer(3000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (screenFinished()) {
					cluePanel.showResultPage(tileArr);
					((Timer) e.getSource()).stop();
				}
			}
		});
		tm.setInitialDelay(60000); // ideal time is before last clue is released
		tm.start();
	}

	public void vaporizeIt(String s) {
		for (ClueTile t: tileArr) {
			if (s.equalsIgnoreCase(t.answer)) t.vaporizeIt = true;
		}
	}
	int alpha;
	private void fadeOut(ClueTile tile) {
		alpha = 255;
		Timer t = new Timer(5,new ActionListener(){
            public void actionPerformed(ActionEvent e)
            { 
				tile.setForeground(new Color(0,0,0,alpha--));
                if(alpha==0) ((Timer) e.getSource()).stop();
            }
        });
        t.setInitialDelay(100);
        t.start();
	}
	private boolean screenFinished() {
		for (ClueTile tile: tileArr) {
			if (!tile.isDone) return false;
		}
		return true;
	}

	private Timer timer;
	private void dropClueTile(ClueTile tile, int delay) {
		timer = new Timer(speed, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tile.isFalling) {
					tile.isFalling = true;
					tile.makeVisible();
				}
				if (tile.vaporizeIt) {
					((Timer) e.getSource()).stop();
					fadeOut(tile);
					cluePanel.correctAnswer++;
					tile.isSolved = true;
					tile.isDone = true;
				}
				
				if (tile.getY()<=380) { // in screen
					Thread t = new Thread(new Runnable() {
						public void run() {
							tile.drop();
						}
					});
					t.start();
				} else { // exiting screen
					if (mode==1);
					if (mode==2) {
						cluePanel.showAnswer(tile.answer.toUpperCase());
					}
					tile.isDone = true;
					((Timer) e.getSource()).stop();
				}
			}
		});
		timer.setInitialDelay(delay);
		timer.start();
		
			
	}

}
