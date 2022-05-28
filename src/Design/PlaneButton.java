package Design;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * @author NaingNaingOo
 *This class extends JButton to create a button with custom design
 */
public class PlaneButton extends JButton {
	
	public PlaneButton(String buttonText,int size) {

		super(buttonText);
		setBorder(BorderFactory.createEmptyBorder());
		setFocusable(false);
		setContentAreaFilled(false);
		setFont(new Font("Times New Roman", Font.BOLD, size));
	
	}
}
