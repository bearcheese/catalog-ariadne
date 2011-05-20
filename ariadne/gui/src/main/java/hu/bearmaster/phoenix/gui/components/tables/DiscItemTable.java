package hu.bearmaster.phoenix.gui.components.tables;

import java.util.List;

import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.bearmaster.phoenix.common.model.DiscItem;

public class DiscItemTable extends CommonTable<DiscItem> {

	private static final Logger LOG = LoggerFactory.getLogger(DiscItemTable.class);
	
	public DiscItemTable(List<DiscItem> initialItemList) {
		super(initialItemList, "discItemTable", new String[]{
				"id",
				"name",
				"path",
				"length"
		});
		setPreferredWidths(new int[]{50,250,450,100});
	}
	
	@Override
	protected void configureTable(JTable table) {
		super.configureTable(table);
		table.setDefaultRenderer(Long.class, new SizeCellRenderer());
		LOG.info("Selection foreground={}", table.getSelectionForeground());
		LOG.info("Selection background={}", table.getSelectionBackground());
	}

	public int getRowOfItem(DiscItem discItem) {
		return getFinalEventList().indexOf(discItem);
	}

}
