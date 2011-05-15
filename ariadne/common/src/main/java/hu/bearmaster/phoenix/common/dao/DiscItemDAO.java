package hu.bearmaster.phoenix.common.dao;

import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;

import java.util.List;


//FIXME dokumentáció!
public interface DiscItemDAO extends GenericDAO<DiscItem, Long>{
	
	List<DiscItem> findByName(String name);
	
	List<DiscItem> findByName(Disc disc, String name);
	
	List<DiscItem> findByDisc(Disc disc);
}
