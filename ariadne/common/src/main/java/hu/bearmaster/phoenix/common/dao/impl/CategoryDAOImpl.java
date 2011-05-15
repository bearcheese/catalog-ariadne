package hu.bearmaster.phoenix.common.dao.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import hu.bearmaster.phoenix.common.dao.CategoryDAO;
import hu.bearmaster.phoenix.common.model.Category;

public class CategoryDAOImpl extends GenericDAOImpl<Category, Long> implements
		CategoryDAO {

	public Category findByName(String name) {
		Criterion nameEq = Restrictions.eq("name", name);
		List<Category> list = findByCriteria(nameEq);
		return !list.isEmpty() ? list.get(0) : null;
	}

}
