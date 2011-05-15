package hu.bearmaster.phoenix.gui.components.tables;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;

import hu.bearmaster.phoenix.common.model.ItemProperty;

public class DiscItemPropertyTable extends CommonTable<ItemProperty> {

	public DiscItemPropertyTable() {
		this(Collections.<ItemProperty>emptyList());
	}
	
	public DiscItemPropertyTable(List<ItemProperty> initialItemList) {
		super(initialItemList, "discItemPropertyTable", new String[] {
				"name",
				"value"
		});
	}
	
	@Override
	protected void configureTable(JTable table) {
		super.configureTable(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

}
