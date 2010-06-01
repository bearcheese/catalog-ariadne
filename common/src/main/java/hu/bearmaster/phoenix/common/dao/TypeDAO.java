package hu.bearmaster.phoenix.common.dao;

import hu.bearmaster.phoenix.common.model.Type;

public interface TypeDAO extends GenericDAO<Type, Long> {

	Type findByName(String name);
	
}
