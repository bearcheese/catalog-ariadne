package hu.bearmaster.phoenix.common.services.impl;

import hu.bearmaster.phoenix.common.dao.CategoryDAO;
import hu.bearmaster.phoenix.common.dao.DiscDAO;
import hu.bearmaster.phoenix.common.dao.DiscItemDAO;
import hu.bearmaster.phoenix.common.dao.TypeDAO;
import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.common.model.Type;
import hu.bearmaster.phoenix.common.services.PersistenceService;

import java.util.List;

/**
 * Service implementor class for {@link PersistenceService} interface.
 * @author "Zoltan Molnar"
 *
 */
public class PersistenceServiceImpl implements PersistenceService { // NOPMD by "Zoltan Molnar" on 2010.05.15. 20:19

	private final DiscDAO discDao;
	private final DiscItemDAO discItemDao;
	private final CategoryDAO categoryDao;
	private final TypeDAO typeDao;
	
	/**
	 * Creates a new service implementation object. Probably it will be used by Spring
	 * depency injection.
	 * @param discDao disc handler DAO object
	 * @param discItemDao disc item handler DAO object
	 */
	public PersistenceServiceImpl(final DiscDAO discDao, final DiscItemDAO discItemDao, 
			final CategoryDAO categoryDao, final TypeDAO typeDao) {
		super();
		this.discDao = discDao;
		this.discItemDao = discItemDao;
		this.categoryDao = categoryDao;
		this.typeDao = typeDao;
	}

	/**
	 * @see PersistenceService#deleteDisc(Disc)
	 */
	public boolean deleteDisc(final Disc disc) {
		//TODO implement disc deletion
		throw new UnsupportedOperationException("Disc deletion not implemented yet!");
	}
	
	/**
	 * @see PersistenceService#generateReport() 
	 */
	public String generateReport() {
		// TODO implement report generation!
		throw new UnsupportedOperationException("Report generation not implemented yet!");
	}

	/**
	 * @see PersistenceService#getAllDiscs()
	 */
	public List<Disc> getAllDiscs() {
		return discDao.findAll();
	}

	/**
	 * @see PersistenceService#loadDisc(Long)
	 */
	public Disc loadDisc(final Long id) { // NOPMD by "Zoltan Molnar" on 2010.05.15. 20:19
		return discDao.findByID(id);
	}

	/**
	 * @see PersistenceService#search()
	 */
	public List<Disc> search() {
		// TODO implement search
		throw new UnsupportedOperationException("Disc search not implemented yet!");
	}

	/**
	 * @see PersistenceService#storeDisc(Disc)
	 */
	public void storeDisc(final Disc disc) {
		discDao.save(disc);
	}

	/**
	 * @see PersistenceService#loadDiscItem(Long)
	 */
	public DiscItem loadDiscItem(final Long id) { // NOPMD by "Zoltan Molnar" on 2010.05.15. 20:19
		return discItemDao.findByID(id);
	}

	/**
	 * @see PersistenceService#loadDiscItems(Disc)
	 */
	public List<DiscItem> loadDiscItems(final Disc disc) {
		return discItemDao.findByDisc(disc);
	}

	/**
	 * @see PersistenceService#storeDiscItem(DiscItem)
	 */
	public void storeDiscItem(final DiscItem item) {
		discItemDao.save(item);
	}
	
	/**
	 * @see PersistenceService#storeDiscItem(Disc, DiscItem)
	 */
	public void storeDiscItem(final Disc disc, final DiscItem item) {
		item.setDisc(disc);
		discItemDao.save(item);
	}

	
	/**
	 * @see PersistenceService#storeDiscItems(List)
	 */
	public void storeDiscItems(final List<DiscItem> items) {
		for(DiscItem di : items){
			discItemDao.save(di);
		}
	}
	
	/**
	 * @see PersistenceService#storeDiscItems(Disc, List)
	 */
	public void storeDiscItems(final Disc disc, final List<DiscItem> items) {
		for(DiscItem di : items){
			storeDiscItem(disc, di);
		}
	}

	/**
	 * @see hu.bearmaster.phoenix.common.services.PersistenceService#loadEntireDisc(java.lang.Long)
	 */
	public ScanResult loadEntireDisc(final Long id) { // NOPMD by "Zoltan Molnar" on 2010.05.15. 20:19
		final Disc disc = loadDisc(id);
		final List<DiscItem> items = loadDiscItems(disc);
		
		return new ScanResult(null, disc, items, null);
	}

	/**
	 * @see PersistenceService#storeEntireDisc(ScanResult)
	 */
	public void storeEntireDisc(final ScanResult result) {
		final Disc disc = result.getDisc();
		final List<DiscItem> items = result.getDiscItems();
		
		storeDisc(disc);
		storeDiscItems(disc, items);
	}

	@Override
	public Category addNewCategory(Category category) {
		categoryDao.save(category);
		return category;
	}

	@Override
	public Category addNewCategory(String categoryName, String description) {
		Category category = new Category(categoryName, description);
		//TODO check uniqueness?
		categoryDao.save(category);
		return category;
	}

	@Override
	public Type addNewType(Type type) {
		typeDao.save(type);
		return type;		
	}

	@Override
	public Type addNewType(String typeName) {
		Type type = new Type(typeName);
		typeDao.save(type);
		return type;
	}

	@Override
	public List<Disc> findAllDiscsInCategory(Category category) {
		return discDao.findAllDiscsInCategory(category);
	}

	@Override
	public List<Disc> findAllDiscsWithType(Type type) {
		return discDao.findAllDiscsWithType(type);
	}

	@Override
	public Category findCategoryByName(String categoryName) {
		return categoryDao.findByName(categoryName);
	}

	@Override
	public Type findTypeByName(String typeName) {
		return typeDao.findByName(typeName);
	}

	@Override
	public List<Category> loadCategories() {
		return categoryDao.findAll();
	}

	@Override
	public List<Type> loadTypes() {
		return typeDao.findAll();
	}

}
