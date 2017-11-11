import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class BackButtonListener implements MouseListener {
	private MenuPanel menuPanel;
	private JButton button;
	public BackButtonListener(MenuPanel menuPanel, JButton button) {
		this.menuPanel = menuPanel;
		this.button = button;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

		if (menuPanel.pageTracking==1) { // back to start page
			menuPanel.clearPackagePage();
			menuPanel.showStartPage();
			menuPanel.remove(menuPanel.backButton);;
			menuPanel.pageTracking--;
		} else if (menuPanel.pageTracking==2) { // back to package page
			menuPanel.clearSetPage();
			menuPanel.showPackage();
			menuPanel.pageTracking--;
		} else if (menuPanel.pageTracking==3) { // back to set page
			menuPanel.clearSpeedPage();
			menuPanel.showSet();
			menuPanel.pageTracking--;
		} else if (menuPanel.pageTracking==4) {
			menuPanel.clearModePage();
			menuPanel.showSpeed();
			menuPanel.pageTracking--;
		} else if (menuPanel.pageTracking==5) {
			menuPanel.clearPlayPage();
			menuPanel.showMode();
			menuPanel.pageTracking--;
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		button.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
	}

}
