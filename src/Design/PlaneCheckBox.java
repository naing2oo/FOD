package Design;
import javax.swing.JCheckBox;

/**
 * @author NaingNaingOo
 *This class extend JCheckBox to create a CheckBox with no fill area 
 */
public class PlaneCheckBox extends JCheckBox{
	public PlaneCheckBox(){
		setContentAreaFilled(false);
	}
}
