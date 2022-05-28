package Design;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * @author NaingNaingOo
 * This class extends DefaultCellEditor to override some function, so that the components of table cell can be edit
 *
 */
public class ComponentCellEditor extends DefaultCellEditor {
	String option="";
	public ComponentCellEditor(JCheckBox checkBox) {
		super(checkBox);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof JButton)
			return (JButton) value;
		else if (value instanceof JTextField)
			return (JTextField) value;
        else if(value instanceof Box) 
        	return (Box)value;
        else  	
           	return super.getTableCellEditorComponent(table, value, isSelected, row, column); 
	}
	
	public boolean stopCellEditing() {
        return true;
    }
}
