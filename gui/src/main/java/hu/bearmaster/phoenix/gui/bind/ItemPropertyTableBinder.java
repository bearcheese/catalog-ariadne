package hu.bearmaster.phoenix.gui.bind;

import hu.bearmaster.phoenix.gui.components.tables.DiscItemPropertyTable;

import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.support.AbstractBinder;
import org.springframework.richclient.util.Assert;

public class ItemPropertyTableBinder extends AbstractBinder {
	
	private static final Logger LOG = Logger.getLogger(ItemPropertyTableBinder.class);
	
	private DiscItemPropertyTable itemPropertyTable;
	
	public ItemPropertyTableBinder() {
		super(List.class, new String[]{});
	}

	@Override
	protected JComponent createControl(Map context) {
		itemPropertyTable = new DiscItemPropertyTable();
		return itemPropertyTable.getControl();
	}

	@Override
	protected Binding doBind(JComponent control, FormModel formModel,
			String formPropertyPath, Map context) {
		Assert.isTrue(control instanceof JTable, "Control must be an instance of JTable.");
		return new ItemPropertyTableBinding(itemPropertyTable, formModel, formPropertyPath);
	}

}
