import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * Bad Design
 */
public class Effect {
	private Component label;
	private int period;
	private int initialDelay;

	public Effect(Component label, int period, int initialDelay) {
		this.label = label;
	}
	
	private int alphaA;
	public void fadeIn() {
		alphaA = 0;
		Timer t = new Timer(period, new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
				label.setForeground(new Color(0,0,0,alphaA++));
				label.setBackground(new Color(255,255,255,alphaA));
		        if(alphaA>=250) ((Timer) e.getSource()).stop();
            }
        });
        t.setInitialDelay(initialDelay);
        t.start();
	} 
	
	// unused
	private int alphaB;
	public void fadeOut() {
		alphaB = 255;
		Timer t = new Timer(period,new ActionListener(){
            public void actionPerformed(ActionEvent e)
            { 
				label.setForeground(new Color(0,0,0,alphaB--));
				label.setBackground(new Color(255,255,255,alphaB));
		        if(alphaB==0) ((Timer) e.getSource()).stop();

            }
        });
        t.setInitialDelay(initialDelay);
        t.start();
	}
}
