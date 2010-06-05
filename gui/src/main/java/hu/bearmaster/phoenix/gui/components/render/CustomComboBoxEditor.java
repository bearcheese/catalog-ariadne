package hu.bearmaster.phoenix.gui.components.render;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

import org.springframework.richclient.list.ComboBoxAutoCompletion;

/**
 * Helper class to provide a custom ComboBoxEditor implementation, 
 * which uses a given {@link CustomObjectRenderer} object to determine
 * what string should be displayed in the editor field. 
 * This was necessary, because Spring RCP intercepts the ComboBox bindings 
 * with {@link ComboBoxAutoCompletion} implementation, which uses a ComboBoxEditor
 * implementation to make the JComboBox searcheable, while it's set to non-editable.
 * 
 * @author "Zoltan Molnar"
 *
 */
public class CustomComboBoxEditor implements ComboBoxEditor {
	
	private Object item;
	private CustomObjectRenderer renderer;
	private JTextField editor;
	
	public CustomComboBoxEditor(CustomObjectRenderer renderer) {
		this.editor = new JTextField();
		this.renderer = renderer;
	}

	@Override
	public void addActionListener(ActionListener l) {
		editor.addActionListener(l);			
	}

	@Override
	public Component getEditorComponent() {
		return editor;
	}

	@Override
	public Object getItem() {
		return item;
	}

	@Override
	public void removeActionListener(ActionListener l) {
		editor.removeActionListener(l);			
	}

	@Override
	public void selectAll() {
		editor.selectAll();
		editor.requestFocus();
	}

	@Override
	public void setItem(Object anObject) {
		if ( anObject != null ) {
			editor.setText(renderer.getRenderValue(anObject));
		} else {
			editor.setText("");
		}
	}
}
