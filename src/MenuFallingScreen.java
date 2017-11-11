import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Code skimmed from FallingScreen Class
 */
public class MenuFallingScreen extends JPanel{
	private String[] words = {"MISSING VOWELS","FALLNG CLUES","THS IS FUN","LOVE THIS GAME!",
								"THIS IS AWESOME!","THIS IS DOPE!"};
	private int[] pos_x = {180,140,70,160,210,50};
	private int speed = 500, delay = 1000;
	private ClueTile vaporizedTile; // choose one tile to vaporize because two generate bugs
	public MenuFallingScreen() {	
		setLayout(null);
		setSize(360,170);
		init(words);
		setOpaque(false);
	}
	private void init(String[] clues) {
		for (int i=0; i<words.length; i++) {
			ClueTile tile = new ClueTile(words[i]);
			tile.setSize(tile.getPreferredSize()); // null layout requires this
			tile.setBounds(pos_x[i], 40, tile.getWidth(), tile.getHeight());
			tile.makeInvisible();
			if (i==1) vaporizedTile = tile;
			add(tile);
			dropClueTile(tile);
			delay+=3000;
		}
	}
	private Timer timer;
	private void dropClueTile(ClueTile tile) {
		timer = new Timer(speed, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tile.isFalling) {
					tile.isFalling = true;
					tile.makeVisible();
				}
				if (tile.getY()>50 && tile.equals(vaporizedTile)) {
					((Timer) e.getSource()).stop();
					fadeOut(tile);
				}
				if (tile.getY()<=150) { // in screen
					Thread t = new Thread(new Runnable() {
						public void run() {
							tile.drop();
						}
					});
					t.start();
				} else { // exiting screen
					((Timer) e.getSource()).stop();
					fadeOut(tile);
				}
			}
		});
		timer.setInitialDelay(delay);
		timer.start();
			
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
}
