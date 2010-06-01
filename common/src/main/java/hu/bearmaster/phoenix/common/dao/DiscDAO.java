package hu.bearmaster.phoenix.common.dao;

import java.util.List;

import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.Type;

//FIXME dokumentáció!
public interface DiscDAO extends GenericDAO<Disc, Long>{
	
	Disc findByName(String name);
	
	Disc findByVolumeName(String vname);

	List<Disc> findAllDiscsInCategory(Category category);
	
	List<Disc> findAllDiscsInCategory(String category);
	
	List<Disc> findAllDiscsWithType(Type type);
	
	List<Disc> findAllDiscsWithType(String typeName);
	
	
}
