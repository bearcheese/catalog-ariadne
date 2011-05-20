package hu.bearmaster.phoenix.gui.bind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.bearmaster.phoenix.common.model.ItemProperty;
import hu.bearmaster.phoenix.gui.components.tables.DiscItemPropertyTable;

import javax.swing.JComponent;

import org.springframework.binding.form.FormModel;
import org.springframework.binding.value.ValueModel;
import org.springframework.richclient.form.binding.support.AbstractBinding;

public class ItemPropertyTableBinding extends AbstractBinding {

	private DiscItemPropertyTable itemPropertyTable;

	public ItemPropertyTableBinding(DiscItemPropertyTable itemPropertyTable, FormModel formModel, String formPropertyPath) {
		super(formModel, formPropertyPath, List.class);
		this.itemPropertyTable = itemPropertyTable;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JComponent doBindControl() {
		final ValueModel valueModel = getValueModel();
        try {
        	ArrayList<ItemProperty> itemProps = new ArrayList<ItemProperty>();
			itemProps.addAll((Collection) valueModel.getValue());
        	itemPropertyTable.setItemList(itemProps);
        }
        catch (ClassCastException e) {
            IllegalArgumentException ex = new IllegalArgumentException("Class cast exception converting '"
                    + getProperty() + "' property value to ItemProperty!");
            ex.initCause(e);
            throw ex;
        }
        //TODO implement ValueCommitPolicies
        //new AsYouTypeTextComponentAdapter(textComponent, valueModel);
        return itemPropertyTable.getControl();
	}

	@Override
	protected void enabledChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readOnlyChanged() {
		// TODO Auto-generated method stub

	}

}
