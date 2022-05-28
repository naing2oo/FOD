package Design;

import java.awt.Font;
import javax.swing.JRadioButton;

/**
 * @author NaingNaingOo
 *This class extends JRadioButton with custom design
 */
public class RadioButton extends JRadioButton {
	public RadioButton(String text,int size) {
		setText(text);
		setFont(new Font("Times New Roman", Font.PLAIN, size));
		setFocusable(false);
		setContentAreaFilled(false);
	}
}
