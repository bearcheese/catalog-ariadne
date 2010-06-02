package hu.bearmaster.phoenix.gui.components.tables;

import hu.bearmaster.phoenix.common.util.SizeFormatter;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;

public class SizeCellRenderer extends DefaultTableCellRenderer {

	private static final Logger LOG = Logger.getLogger(SizeCellRenderer.class);
	
	private static final long serialVersionUID = 8064480108331848586L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object object,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		super.getTableCellRendererComponent(table, object, isSelected, hasFocus, row, column);
		//LOG.info("(" + row + "," + column + ")=> selected=" + isSelected);
		if (column == 0) {
			Long id = (Long) object;
			setText(String.valueOf(id));
		} else {
			Long size = (Long) object;
			setText(SizeFormatter.getDoubleDynamicSuffix(size));
		}
		return this;
	}

}
