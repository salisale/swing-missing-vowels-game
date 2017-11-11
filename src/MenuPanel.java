import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

public class MenuPanel extends JPanel{
	private MyFrame frame;
	private MenuFallingScreen mfs;
	protected JButton playButton, backButton;
	private JLabel title, tagline1, tagline2;
	private JLabel p1, p2, p3; //page
	private JLabel s1, s2, s3, s4, s5; //set
	private JLabel sp1, sp2, sp3; //speed
	private JLabel m1, m2, modeDesc;//mode
	private JLabel play;
	int pageTracking = 0; // 1 = package, 2 = set... LabelListener++ backButton--
	
	public MenuPanel(MyFrame frame) {
		//frame = (MyFrame) SwingUtilities.getWindowAncestor(this);
		this.frame = frame;
		mfs = new MenuFallingScreen();
		setLayout(new MigLayout("wrap 1","[450px]","[500px]"));
		init();
		
	}
	public void init() {
		title = new JLabel("MISSING VOWELS");
		title.setFont(new Font("Apple Chancery", Font.BOLD, 40));
		title.setIcon(GIcon.getIcon("Image/logo.png", 420, 120));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setHorizontalTextPosition(JLabel.CENTER);
		add(title, "dock north");

		tagline1 = new JLabel();
		tagline1.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
		tagline1.setText("Guess well-known names, phrases and words where");
		tagline1.setHorizontalAlignment(JLabel.CENTER);
		add(tagline1, "dock north");
		
		tagline2 = new JLabel();
		tagline2.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
		tagline2.setText("vwls have been taken out and cn snnts re-spaced!");
		tagline2.setHorizontalAlignment(JLabel.CENTER);
		add(tagline2, "dock north");
		
		// Not yet add: add in showStartPage
		playButton = new JButton("CHOOSE YOUR SPECIALTY");
		playButton.setFont(new Font("Apple Chancery", Font.BOLD, 20));
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPackage();
				pageTracking++;
				clearStartPage(); //remove itself
			}
		});
		
		// Not yet add: add in showPackagePage
		backButton = new JButton("BACK"); 
		backButton.setFont(new Font("Apple Chancery", Font.BOLD, 15));
		backButton.setPreferredSize(new Dimension(40,20));
		backButton.addMouseListener(new BackButtonListener(this, backButton));
		
		fadeInComponents();
		showStartPage(); // add playButton and menuFallingScreen
	}
	private void fadeInComponents() {
		for (int i=0; i<this.getComponentCount(); i++) {
			Effect ef = new Effect(this.getComponent(i), 20, 50);
			ef.fadeIn();
		}
	}
	
	int currPackage = 0;
	int currSet = 0;
	int currSpeed = 0;
	int currMode = 0;
	
	public void clearStartPage() {
		remove(mfs);
		remove(playButton);
	}
	public void clearPackagePage() {
		remove(p1); remove(p2); remove(p3);
	}
	public void clearSetPage() {
		remove(s1); remove(s2); remove(s3); remove(s4); remove(s5);
	}
	public void clearSpeedPage() {
		remove(sp1); remove(sp2); remove(sp3); 
	}
	public void clearModePage() {
		remove(modeDesc); remove(m1); remove(m2);
	}
	public void clearPlayPage() {
		remove(play);
	}
	public void showStartPage() {
		add(playButton, "dock south"); // add playButton and ease in manually
		new Effect(backButton, 20, 50).fadeIn();
		mfs = new MenuFallingScreen(); // add menuFallingScreen
		add(mfs, "grow");
		repaint(); revalidate();
	}
	public void showPackage() {
		add(backButton,"dock south, w 80!");
		new Effect(backButton, 20, 50).fadeIn();
		
		p1 = createAndShowLabel("ENTERTAINMENT", 25);
		p2 = createAndShowLabel("ACADEMIC SUBJECT", 25);
		p3 = createAndShowLabel("VOCABULARY", 25);
		repaint(); revalidate();
		
	}
	public void showSet() {
		String[] text = CategoryFile.getNames(currPackage);
		s1 = createAndShowLabel("I. " + text[0], 16);
		s2 = createAndShowLabel("II. " + text[1], 16);
		s3 = createAndShowLabel("III. " + text[2], 16);
		s4 = createAndShowLabel("IV. " + text[3], 16);
		s5 = createAndShowLabel("V. " + text[4], 16);
		repaint(); revalidate();
	}

	public void showSpeed() {
		sp1 = createAndShowLabel("SPEED 1", 22);
		sp2 = createAndShowLabel("SPEED 2", 22);
		sp3 = createAndShowLabel("SPEED 3", 22);
		repaint(); revalidate();
	}
	public void showMode() {
		modeDesc = new JLabel("Do you want answer displayed for missed clues?");
		modeDesc.setFont(new Font("Apple Chancery", Font.PLAIN, 14));
		modeDesc.setHorizontalTextPosition(JLabel.CENTER);
		add(modeDesc,"spanx, align 50%");
		Effect ef = new Effect(modeDesc, 20, 50); 
		ef.fadeIn();
		
		m1 = createAndShowLabel("NO DISPLAY", 22);
		m2 = createAndShowLabel("DISPLAY", 22);
		repaint(); revalidate();
	}
	public void showPlay() {
		play = createAndShowLabel("PLAY", 35);
		repaint(); revalidate();
	}
	private JLabel createAndShowLabel(String text, int fontSize) {
		JLabel l = new JLabel(text);
		l.setFont(new Font("Apple Chancery", Font.PLAIN, fontSize));
		l.setHorizontalTextPosition(JLabel.CENTER);
		l.addMouseListener(new LabelListener(this, l));
		add(l,"spanx, align 50%");
		Effect ef = new Effect(l, 20, 50); // period, initialDelay
		ef.fadeIn();
		return l;
	}
	public void goToCluePanel() {
		String cf = CategoryFile.getFileName(currPackage, currSet);
		frame.update(getGraphics());
		frame.addCluePanel(cf, currSpeed, currMode);
	}
	public void backToSetPage() { // call after a game is finished
		pageTracking = 2;
		add(backButton, "dock south, w 80!");
		showSet();
	}


}
