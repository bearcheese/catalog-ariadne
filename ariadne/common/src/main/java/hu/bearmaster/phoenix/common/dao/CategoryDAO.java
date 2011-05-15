package hu.bearmaster.phoenix.common.dao;

import hu.bearmaster.phoenix.common.model.Category;

public interface CategoryDAO extends GenericDAO<Category, Long> {

	Category findByName(String name);
	
}
