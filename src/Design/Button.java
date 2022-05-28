package Design;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;


/**
 * @author NaingNaingOo 
 *This class extends JButton to override some function for design purpose;
 */
public class Button extends JButton{
	public Button(String label){
		super(label);
		setBorder(BorderFactory.createLineBorder(Color.green));
		setFocusable(false);
		setContentAreaFilled(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 16));
	}

}
