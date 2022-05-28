package Design;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author NaingNaingOo
 *This class extend JLabel to create a label with image and text below the image
 */
public class Imagelabel extends JLabel{
	public Imagelabel(String label,Icon bug){
		super(label);
		setIcon(bug);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setFont(new Font("Times New Roman", Font.BOLD, 16));
	}

}
