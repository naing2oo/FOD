package Design;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
/**
 * @author NaingNaingOo
 *This class extends JLabel to create a label with custom design
 */
public class Label extends JLabel {
	
	public Label(String string,Color color,int fontsize){
		super(string);
		setForeground(color);
		setFont(new Font("Times New Roman", Font.PLAIN, fontsize));
		
	}

}
