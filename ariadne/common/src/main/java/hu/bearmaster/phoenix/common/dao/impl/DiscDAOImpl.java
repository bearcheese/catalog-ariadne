package hu.bearmaster.phoenix.common.dao.impl;

import hu.bearmaster.phoenix.common.dao.CategoryDAO;
import hu.bearmaster.phoenix.common.dao.DiscDAO;
import hu.bearmaster.phoenix.common.dao.TypeDAO;
import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.Type;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;


public class DiscDAOImpl extends GenericDAOImpl<Disc, Long> implements DiscDAO{

	private static final Logger LOG = LoggerFactory.getLogger(DiscDAOImpl.class);
	
	private CategoryDAO categoryDao;
	private TypeDAO typeDao;
	
	public CategoryDAO getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}

	public TypeDAO getTypeDao() {
		return typeDao;
	}

	public void setTypeDao(TypeDAO typeDao) {
		this.typeDao = typeDao;
	}
	
	public Disc findByName(final String name) {
		Criterion nameEq = Restrictions.eq("name", name);
		List<Disc> list = findByCriteria(nameEq);
		return !list.isEmpty() ? list.get(0) : null;
	}
	
	public Disc findByVolumeName(final String vname) {
		Criterion nameEq = Restrictions.eq("volumename", vname);
		List<Disc> list = findByCriteria(nameEq);
		return !list.isEmpty() ? list.get(0) : null;
	}
	
	public List<Disc> findAllDiscsInCategory(Category category) {
		Criterion categoryEq = Restrictions.eq("category", category);
		List<Disc> list = findByCriteria(categoryEq);
		return list;
	}
	
	public List<Disc> findAllDiscsInCategory(String categoryName) {
		if ( categoryDao != null ) {
			Category category = categoryDao.findByName(categoryName);
			if ( category != null ) {
				return findAllDiscsInCategory(category);
			}
		}
		else {
			LOG.error("CategoryDAO is not set, can't lookup for categories!");
		}
		return null;
	}
	
	public List<Disc> findAllDiscsWithType(Type type) {
		Criterion typeEq = Restrictions.eq("type", type);
		List<Disc> list = findByCriteria(typeEq);
		return list;
	}

	public List<Disc> findAllDiscsWithType(String typeName) {
		if ( typeDao != null ) {
			Type type = typeDao.findByName(typeName);
			if ( type != null ) {
				return findAllDiscsWithType(type);
			}
		}
		else {
			LOG.error("TypeDAO is not set, can't lookup for types!");
		}
		return null;
	}		
}
