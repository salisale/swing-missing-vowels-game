import java.awt.Image;

import javax.swing.ImageIcon;

public class GIcon {
	/**
	 * Resizes image to a specified size
	 * For any image in resources folder
	 * String must be in format of "sub-folder/name.xxx"
	 */
	public static ImageIcon getIcon(String fileName, int w, int h) { 
		ImageIcon origIcon = new ImageIcon(GIcon.class.getResource( //ImageIcon
				"resources/" + fileName));
		Image resizedIcon = origIcon.getImage().getScaledInstance(w, h, //Image
				java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resizedIcon);
	}
}
