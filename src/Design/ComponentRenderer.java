package Design;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
 
/**
 * @author NaingNaingOo
 *This class extends DefaultTableCellRenderer to override some function, so that the components of table cell will be show as it's value
 */
public class ComponentRenderer extends DefaultTableCellRenderer{
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column)
     {		
    	setHorizontalAlignment(SwingConstants.CENTER);
    	
        if(value instanceof JLabel){
           //This time return only the JLabel without icon
            return (JLabel)value;
        }
        else if(value instanceof JButton) {
        	return (JButton)value;
        }
        else if(value instanceof Box) {
        	return (Box)value;
        }
        else if(value instanceof JTextField) {
        	return (JTextField) value;
        }
        else{
        	
        	return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
     }
}