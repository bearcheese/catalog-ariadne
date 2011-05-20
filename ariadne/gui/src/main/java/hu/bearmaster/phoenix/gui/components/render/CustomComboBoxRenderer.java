package hu.bearmaster.phoenix.gui.components.render;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Helper class for providing a custom combobox renderer. 
 * This class uses the given {@link CustomObjectRenderer} object to determine what string 
 * should be displayed for each item in the dropdown list.
 * 
 * @author "Zoltan Molnar"
 *
 */
public class CustomComboBoxRenderer extends JLabel implements ListCellRenderer {
	
	private static final long serialVersionUID = 7805248236974879571L;

	private CustomObjectRenderer renderer;
	
	public CustomComboBoxRenderer(CustomObjectRenderer renderer) {
		setOpaque(true);
		this.renderer = renderer;
	}
	
    public Component getListCellRendererComponent(JList list, Object value, 
    		int index, boolean isSelected, boolean cellHasFocus) {

    	if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

    	setText(renderer.getRenderValue(value));
    	setFont(list.getFont());
    	
        return this;
    }

}
