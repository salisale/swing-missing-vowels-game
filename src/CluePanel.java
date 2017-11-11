import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

public class CluePanel extends JPanel{
	private MyFrame frame;
	private JLabel groupLabel;
	private String[] answer;
	private JTextField entry; // player's input
	private FallingScreen screen;
	private JLabel answerLabel;
	int correctAnswer = 0;
	
	public CluePanel(MyFrame frame, String[] clues, String group, int speed, int mode) {
		this.frame = frame;
		setLayout(new MigLayout("wrap 1",
				"[450px]", // this is override by JFrame size
				"[500px]"));
		
		answer = new String[clues.length];
		for (int i=0; i<clues.length; i++) {
			answer[i] = clues[i].toLowerCase();
		}
		
		// Set Group Label
		groupLabel = new JLabel(group.toUpperCase());
		
		groupLabel.setFont(new Font("Courier", Font.BOLD, 16));
		groupLabel.setSize(groupLabel.getPreferredSize());
		groupLabel.setHorizontalAlignment(JLabel.CENTER);
		add(groupLabel, "dock north, spanx");
		
		// Set Falling Screen
		screen = new FallingScreen(this, answer, speed, mode);
		add(screen, "grow");
		
		
		// Set Text Field
		entry = new JTextField(20);
		add(entry, "dock south, spanx");
		entry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Arrays.asList(answer).contains(entry.getText())) {
					correctEntry();
				} else {
					incorrectEntry();
				}
			}
		});
		
		// Set Answer Label
		answerLabel = new JLabel(" ");
		answerLabel.setFont(new Font("Courier", Font.PLAIN, 15));
		answerLabel.setPreferredSize(new Dimension(200,40));
		answerLabel.setHorizontalAlignment(JLabel.CENTER);
		add(answerLabel, "dock south");
		
	}

	private void incorrectEntry() {
		entry.setText("");
	}
	private void correctEntry() {
		screen.vaporizeIt(entry.getText());
		entry.setText("");
	}

	public void showAnswer(String s) {
		answerLabel.setText(s);
	}

	private JButton nextButton;
	private JTextArea result;
	private JScrollPane scPane;
	
	public void showResultPage(ClueTile[] tileArr) {
		
		remove(screen); remove(entry); remove(answerLabel);
		
		// display area
		result = new JTextArea();
		result.setEditable(false);
		result.setOpaque(false);
		result.setFont(new Font("Courier", Font.PLAIN, 14));
		
		nextButton = new JButton("NEXT");
		nextButton.setFont(new Font("Apple Chancery", Font.BOLD, 15));
		nextButton.setPreferredSize(new Dimension(40,20));
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showIncorrectAnswers(tileArr);
			}
			
		});
		
		add(nextButton, "dock south, w 80!, align right");
		showCorrectAnswers(tileArr);
	}
	private void showCorrectAnswers(ClueTile[] tileArr) {
		String text = "\n\tCorrect Answer : " + correctAnswer + "\n\n";
		for (ClueTile tile: tileArr) {
			if (tile.isSolved) {
				text += "\t" + tile.clue + "\n\t" + tile.answer.toUpperCase() + "\n\n";
			} 
		}
		result.setText(text);
		result.setCaretPosition(0);
		scPane = new JScrollPane(result);
		scPane.setPreferredSize(this.getSize());
		add(scPane, "grow");
		repaint(); revalidate();
		
	}
	protected void showIncorrectAnswers(ClueTile[] tileArr) {
		remove(scPane);
		String text = "\n\tIncorrect Answer: " + (15-correctAnswer) + "\n\n";
		for (ClueTile tile: tileArr) {
			if (!tile.isSolved) {
				text += "\t" + tile.clue + "\n\n";
			}
		}
		result.setText(text);
		result.setCaretPosition(0);
		scPane = new JScrollPane(result);
		add(scPane, "grow");
		repaint(); revalidate();
		
		// button to return to menuPanel
		nextButton.removeActionListener(nextButton.getActionListeners()[0]);
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.returnToManuPanel();
			}
			
		});
		
	}

}
