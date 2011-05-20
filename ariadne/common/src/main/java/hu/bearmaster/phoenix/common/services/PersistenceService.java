package hu.bearmaster.phoenix.common.services;

import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.common.model.Type;

import java.util.List;

/**
 * Supports the DB related operations for the presentation tier. Loads and stores discs,
 * makes queries in the DB.
 * @author Zoltan_Molnar1
 *
 */
public interface PersistenceService {
	
	/**
	 * Load a given disc with id from the DB
	 * @param id a disc id 
	 * @return an instance of Disc class, loaded from DB
	 */
	Disc loadDisc(Long id);
	
	/**
	 * Stores the given disc in the DB
	 * @param disc
	 */
	void storeDisc(Disc disc);
	
	/**
	 * Deletes the disc from DB
	 * @param disc
	 * @return the success of the deletion
	 */
	boolean deleteDisc(Disc disc);
	
	/**
	 * Load a disc item with the given <code>id</code> from the persistent storage 
	 * @param id disc item id
	 * @return DiscItem object or null if doesn't exists with <code>id</code>
	 */
	DiscItem loadDiscItem(Long id);
	
	/**
	 * Load disc items belonging to the given disc object from the persistent storage
	 * @param disc an already persisted (and attached or detached) Disc object
	 * @return
	 */
	List<DiscItem> loadDiscItems(Disc disc);
	
	/**
	 * Stores the given disc item in the persistent storage
	 * @param item 
	 */
	void storeDiscItem(DiscItem item);
	
	/**
	 * Associates the disc item with the disc and stores the disc item in the persistent storage
	 * @param disc a Disc to be associated with item
	 * @param item a DiscItem
	 */
	void storeDiscItem(Disc disc, DiscItem item);
	
	/**
	 * Stores the list of disc items
	 * @param items
	 */
	void storeDiscItems(List<DiscItem> items);
	
	/**
	 * Associates the list of disc items with the given disc, and stores them in the persistent
	 * storage
	 * @param disc a Disc to be associated with items
	 * @param items list of DiscItem objects
	 */
	void storeDiscItems(Disc disc, List<DiscItem> items);
	
	//TODO deleteDisc this method is missing, TBC it's necessity
	
	/**
	 * Loads a dics and the associated disc items with it from the persistent storage
	 * @param id Disc id
	 * @return a ScanResult object contained with the loaded disc and disc items
	 */
	ScanResult loadEntireDisc(Long id);
	
	/**
	 * Stores the disc and items within the given ScanResult object. Disc items will be 
	 * associated with the disc object before storing.
	 * @param de ScanResult object
	 */
	void storeEntireDisc(ScanResult result);
	
	//TODO deleteEntireDisc??? TBC
	
	void updateDisc(Disc disc);
	
	/**
	 * Makes a search in the DB by the given criterias
	 * @return
	 */
	List<Disc> search(); //TODO paraméterezés megoldása!
	
	/**
	 * Returns all the discs stored in the DB
	 * @return
	 */
	List<Disc> getAllDiscs();
	
	/**
	 * Generates a report by the given criterias
	 * @return
	 */
	String generateReport(); //TODO paraméterezés? ld. search
	
	//Category related tasks
	List<Category> loadCategories();
	
	Category findCategoryByName(String categoryName);
	
	List<Disc> findAllDiscsInCategory(Category category);
	
	Category addNewCategory(Category category);
	
	Category addNewCategory(String categoryName, String description);
	
	
	//Type related tasks
	List<Type> loadTypes();
	
	Type findTypeByName(String typeName);
	
	List<Disc> findAllDiscsWithType(Type type);
	
	Type addNewType(Type type);
	
	Type addNewType(String typeName);

}
