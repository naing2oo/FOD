package Design;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author NaingNaingOo
 *This class extends JButton to create a button with image
 */
public class ImageButton extends JButton {
	static String imgpath="C:/Eclipse2022/FoodOrderingandDelivering/src/Pictures/";
	public ImageButton(String img) {
		
		super(new ImageIcon(imgpath+img+".png"));
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		this.setBorder(null);
	}
}
