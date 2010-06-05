package hu.bearmaster.phoenix.gui.bind;

import hu.bearmaster.phoenix.common.model.Type;
import hu.bearmaster.phoenix.common.services.PersistenceService;
import hu.bearmaster.phoenix.gui.components.render.CustomComboBoxEditor;
import hu.bearmaster.phoenix.gui.components.render.CustomComboBoxRenderer;
import hu.bearmaster.phoenix.gui.components.render.CustomObjectRenderer;

import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.support.AbstractBinder;
import org.springframework.richclient.form.binding.swing.ComboBoxBinding;
import org.springframework.richclient.util.Assert;

/**
 * Binder class for {@link Type} to create a JComboBox on the UI.
 * It will use the provided persistence service to obtain type items.
 * 
 * @author "Zoltan Molnar"
 *
 */
public class DiscTypeBinder extends AbstractBinder {

	private PersistenceService persistenceService;
	
	public DiscTypeBinder(PersistenceService persistenceService) {
		super(Type.class);
		this.persistenceService = persistenceService;
	}
	
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	
	public void setPersistenceService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JComponent createControl(Map context) {
		JComboBox comboBox = getComponentFactory().createComboBox();
		comboBox.setEditable(true);
		return comboBox;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Binding doBind(JComponent control, FormModel formModel,
			String formPropertyPath, Map context) {
		Assert.isTrue(control instanceof JComboBox, formPropertyPath);
		
		ComboBoxBinding binding = new ComboBoxBinding((JComboBox)control, formModel, formPropertyPath);
		List<Type> discTypes = persistenceService.loadTypes();
		
		TypeRenderer renderer = new TypeRenderer();
		
		binding.setRenderer(new CustomComboBoxRenderer(renderer));
		binding.setEditor(new CustomComboBoxEditor(renderer));
        binding.setSelectableItems(discTypes);
        return binding;	
	}
	
	private class TypeRenderer implements CustomObjectRenderer {

		@Override
		public String getRenderValue(Object value) {
			if ( value instanceof Type ) {
				Type type = (Type)value;
				return type.getName();
			}
			return "";
		}
		
	}

}
