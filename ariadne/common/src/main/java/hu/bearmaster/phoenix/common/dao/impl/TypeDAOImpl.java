package hu.bearmaster.phoenix.common.dao.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import hu.bearmaster.phoenix.common.dao.TypeDAO;
import hu.bearmaster.phoenix.common.model.Type;

public class TypeDAOImpl extends GenericDAOImpl<Type, Long> implements TypeDAO {

	public Type findByName(String name) {
		Criterion nameEq = Restrictions.eq("name", name);
		List<Type> list = findByCriteria(nameEq);
		return !list.isEmpty() ? list.get(0) : null;
	}

}
