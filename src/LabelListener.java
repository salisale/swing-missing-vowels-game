import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Label names must be fixed; does not allow for dynamic change in components
 * Bad design
 */
public class LabelListener implements MouseListener{
	private MenuPanel menuPanel;
	private JLabel label;
	public LabelListener(MenuPanel menuPanel, JLabel label) {
		this.label = label;
		this.menuPanel = menuPanel;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		String x = label.getText();
		menuPanel.pageTracking++; // keep track of current page
		if (x.startsWith("EN")||x.startsWith("AC")||x.startsWith("VO")) { // if package, proceed to...
			int pack = x.equals("ENTERTAINMENT")? 1: x.equals("ACADEMIC SUBJECT")? 2: 3;
			menuPanel.currPackage = pack; //record package picked
			menuPanel.clearPackagePage();
			menuPanel.showSet(); 
		} else if (x.startsWith("I")||x.startsWith("V")){ // if set, proceed to...
			int set = x.startsWith("I.")? 1: x.startsWith("II.")? 2: x.startsWith("III.")? 3: x.startsWith("IV.")? 4: 5;
			menuPanel.currSet = set; //record set picked
			menuPanel.clearSetPage();
			menuPanel.showSpeed();
		} else if (x.startsWith("SPEED")) {
			int speed = x.contains("1")? 1: x.contains("2")? 2: 3;
			menuPanel.currSpeed = speed;
			menuPanel.clearSpeedPage();
			menuPanel.showMode();
		} else if (x.startsWith("DIS")||x.startsWith("NO DIS")) {
			int mode = x.startsWith("NO DIS")? 1: 2;
			menuPanel.currMode = mode;
			menuPanel.clearModePage();
			menuPanel.showPlay();
		} else if (x.startsWith("PLAY")) {
			menuPanel.clearPlayPage();
			menuPanel.goToCluePanel();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
