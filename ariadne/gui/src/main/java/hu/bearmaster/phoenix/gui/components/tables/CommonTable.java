package hu.bearmaster.phoenix.gui.components.tables;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.richclient.table.support.AbstractObjectTable;

import ca.odell.glazedlists.EventList;

public class CommonTable<T extends Comparable<? super T>> extends AbstractObjectTable {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonTable.class);
	
	private List<T> initialItems;
	private int[] preferredWidths;
	
	/**
	 * It's the same like constructor of {@link AbstractObjectTable}, but it has an additional
	 * parameter: <code>initialItemList</code>, which represents the data in the table.
	 * 
	 * @param initialItemList the initial data of the table
	 * @param modelId the id of the model
	 * @param columnPropertyNames the names of the columns in a String array
	 */
	protected CommonTable(List<T> initialItemList, String modelId, String[] columnPropertyNames) {
		super(modelId, columnPropertyNames);
		this.initialItems = initialItemList;
	}
	
	/**
	 * Returns an unmodifiable list of the current items in the table (result of {@link AbstractObjectTable#getFinalEventList()}
	 * @return list of current items
	 */
	@SuppressWarnings("unchecked")
	public List<T> getItemList() {
		return Collections.unmodifiableList(getFinalEventList());
	}

	/**
	 * Clears all the items that were in the table previously, and adds the new items to it.
	 * @param itemList new items to be set to the table
	 */
	@SuppressWarnings("unchecked")
	public void setItemList(List<T> itemList) {
		EventList events = getFinalEventList();
		events.clear();
		events.addAll(itemList);
	}

	/**
	 * Array of preferred widths of table columns
	 * @return array of width
	 */
	public int[] getPreferredWidths() {
		return preferredWidths;
	}

	/**
	 * Beállítja az oszlopszélességeket, csak a configureTable metódus hívása előtt van hatása
	 * Sets the preferred width of table columns. Change only takes affect 
	 * after a {@link #configureTable(JTable)} call
	 * @param preferredWidths
	 */
	public void setPreferredWidths(int[] preferredWidths) {
		this.preferredWidths = preferredWidths;
	}
	
	/**
	 * Configures the table with the following settings:
	 * <ul>
	 * 	<li>sets the resize mode to {@link JTable#AUTO_RESIZE_OFF}</li>
	 * 	<li>sets the selection mode to {@link ListSelectionModel#SINGLE_SELECTION}</li>
	 * 	<li>sets the column sizes to preferred widths set by {@link #setPreferredWidths(int[])}</li> 
	 * </ul>
	 * @param table
	 */
	@Override
	protected void configureTable(JTable table) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if(preferredWidths != null) {
			TableColumnModel tcm = table.getColumnModel();
			for(int i = 0; i < preferredWidths.length; i++) {
				tcm.getColumn(i).setPreferredWidth(preferredWidths[i]);
			}
		}
	}

	/**
	 * Method for the super class to get table data
	 * @return
	 */
	@Override
	protected Object[] getDefaultInitialData() {
		return initialItems.toArray();
	}
	
	/**
	 * Returns the selected items in the table.
	 * @return list of selected items
	 */
	@SuppressWarnings("unchecked")
	public List<T> getSelectedItems(){
		int[] selected = getTable().getSelectedRows();
		ArrayList<T> selectedItems = new ArrayList<T>(selected.length);
		for(int i = 0; i < selected.length; i++) {
			//selectedItems.set(i, (T)getTableModel().getElementAt(selected[i]));
			selectedItems.add((T)getTableModel().getElementAt(selected[i]));
		}
		return selectedItems;
	}

	/**
	 * Returns the first item from the selected items
	 * @return the first selected item or null if nothing is selected
	 */
	public T getSelectedItem() {
		List<T> selectedItems = getSelectedItems();
		if ( selectedItems.size() > 0 ) {
			return selectedItems.get(0);
		}			
		return null;
	}
	
	/**
	 * A megadott sorban és oszlopban lévő cellát a látható területre görgeti
	 * Sajnos még nem működik megfelelően, ezért a cella nem a látható terület közepén,
	 * hanem legfelső vagy legalsó sorban fog megjelenni.
	 * @param row
	 * @param column
	 */
	public void showCell(int row, int column) {
		//TODO továbbfejleszteni hogy középen jelenjen meg a cella!
		LOG.info("Set table to show: ({},{})", row, column);
		JTable table = getTable();
		Rectangle rect = table.getCellRect(row, column, true);
		table.scrollRectToVisible(rect);
		table.clearSelection();
		//table.setRowSelectionInterval(row, row);
		table.getSelectionModel().setSelectionInterval(row, row);
		LOG.info("Selection foreground={}", table.getSelectionForeground());
		LOG.info("Selection background={}", table.getSelectionBackground());
		LOG.info("Selected row={}", table.getSelectedRow());
		getTableModel().fireTableDataChanged();
		table.repaint();
	}
	
	@Override
	protected void handleNewObject(Object object) {
		super.handleNewObject(object);
		//Collections.sort();
	}
	
	@Override
	protected void handleDeletedObject(Object object) {
		super.handleDeletedObject(object);
	}

}
