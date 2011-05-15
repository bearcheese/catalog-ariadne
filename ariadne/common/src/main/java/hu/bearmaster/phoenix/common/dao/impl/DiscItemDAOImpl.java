package hu.bearmaster.phoenix.common.dao.impl;

import hu.bearmaster.phoenix.common.dao.DiscItemDAO;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;

import java.util.List;


import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;


public class DiscItemDAOImpl extends GenericDAOImpl<DiscItem, Long> implements DiscItemDAO{

	public List<DiscItem> findByName(String name) {
		Criterion nameEq = Restrictions.eq("name", name);
		return findByCriteria(nameEq);
	}

	public List<DiscItem> findByName(Disc disc, String name) {
		Criterion nameEq = Restrictions.eq("name", name);
		Criterion discEq = Restrictions.eq("disc", disc);
		Criterion conj = Restrictions.and(nameEq, discEq);
		
		return findByCriteria(conj);
	}

	public List<DiscItem> findByDisc(Disc disc) {
		Criterion discEq = Restrictions.eq("disc", disc);
		return findByCriteria(discEq);
	}

}
