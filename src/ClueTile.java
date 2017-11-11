import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class ClueTile extends JLabel {
	String clue;
	String answer;
	boolean isFalling=false;
	boolean vaporizeIt = false;
	boolean isSolved = false;
	boolean isDone = false; // to keep track whether all is finished
	
	public ClueTile(String s) {
		this.answer = s;
		this.clue = Clue.getClue(s);
		setText(clue);
		setFont(new Font("Courier", Font.PLAIN, 15));
		setForeground(Color.BLACK);
	}
	public void drop() {
		this.setBounds(this.getX(), this.getY()+10, this.getWidth(), this.getHeight());
	}
	public void makeInvisible() {
		this.setText("");
	}
	public void makeVisible() {
		this.setText(clue);
	}
}
