import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

/**
 * Main class
 */
public class MyFrame extends JFrame {
	CluePanel currCluePanel;
	static MenuPanel menuPanel;
	
	public MyFrame() {
		setLayout(new MigLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 500);
		setResizable(false);
		setVisible(true);
		menuPanel = new MenuPanel(this);
		add(menuPanel);
	}
	void clearPanel() {
		getContentPane().removeAll();
		repaint();
	}

	void addCluePanel(String clueFile, int speed, int mode) {
		clearPanel();
		InputFile file = new InputFile(clueFile);
		String[] clues = file.getClues();
		currCluePanel = new CluePanel(this, clues, file.getGroup(), speed, mode);
		add(currCluePanel);
	}
	void returnToManuPanel() {
		clearPanel();
		add(menuPanel);
		menuPanel.backToSetPage();
	}
	public static void main(String args[]) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MyFrame();
			}
			
		});
	}
}
