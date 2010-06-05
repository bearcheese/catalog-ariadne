package hu.bearmaster.phoenix.gui.bind;

import hu.bearmaster.phoenix.common.model.Category;
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
 * Binder class for {@link Category} to create a JComboBox on the UI.
 * It will use the provided persistence service to obtain category items.
 * 
 * @author "Zoltan Molnar"
 *
 */
public class DiscCategoryBinder extends AbstractBinder {
	
	private PersistenceService persistenseService;
	
	public DiscCategoryBinder(PersistenceService persServ) {
		super(Category.class);
		this.persistenseService = persServ;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JComponent createControl(Map context) {
		JComboBox comboBox = getComponentFactory().createComboBox();
		comboBox.setEditable(false);
		return comboBox;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Binding doBind(JComponent control, FormModel formModel,
			String formPropertyPath, Map context) {
		
		Assert.isTrue(control instanceof JComboBox, formPropertyPath);
		
		JComboBox comboBox = (JComboBox)control;
		
		ComboBoxBinding binding = new ComboBoxBinding(comboBox, formModel, formPropertyPath);
		
		CategoryRenderer renderer = new CategoryRenderer();
		
		List<Category> discCategories = persistenseService.loadCategories();
		
		binding.setEditor(new CustomComboBoxEditor(renderer));
		binding.setRenderer(new CustomComboBoxRenderer(renderer));
		binding.setSelectableItems(discCategories);
		
		
		return binding;
	}
	
	/**
	 * Renderer implementation to provide render string for Category class 
	 * (to avoid using {@link Category#toString()}
	 * @author "Zoltan Molnar"
	 *
	 */
	private class CategoryRenderer implements CustomObjectRenderer {

		@Override
		public String getRenderValue(Object value) {
			if (value instanceof Category) {
	    		Category category = (Category)value;
	    		
	    		return category.getName();
	    	}
	    	return "";
		}
	}
}
