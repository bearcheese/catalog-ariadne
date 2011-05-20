package hu.bearmaster.phoenix.common.test;

import hu.bearmaster.phoenix.common.dao.CategoryDAO;
import hu.bearmaster.phoenix.common.dao.DiscDAO;
import hu.bearmaster.phoenix.common.dao.DiscItemDAO;
import hu.bearmaster.phoenix.common.dao.TypeDAO;
import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.model.Type;
import hu.bearmaster.phoenix.common.util.SpringUtil;

import java.util.List;

public class TestDb {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if(args.length != 1){
			System.out.println("Usage: java "+TestDb.class.getName()+" [load|save]");
			return;
		}
		if ("save".equals(args[0])) {
			java.sql.Date today = new java.sql.Date(
					new java.util.GregorianCalendar().getTimeInMillis());
			
			CategoryDAO categoryDao = (CategoryDAO)SpringUtil.getBean("categoryDao");
			TypeDAO typeDao = (TypeDAO)SpringUtil.getBean("typeDao");
			
			Category cat = categoryDao.findByName("Film");
			if ( cat == null ) {
				cat = new Category("Film", "Test film category");
				categoryDao.save(cat);
			}
			
			Type type = typeDao.findByName("DVD");
			if ( type == null ) {
				type = new Type("DVD");
				typeDao.save(type);
			}
			
			Disc disc = new Disc("Megint egy Masiklemez", "FooVolume3", cat, type,
					today, "No comment again... and again");
			DiscDAO discDao = (DiscDAO)SpringUtil.getBean("discDao");
			discDao.save(disc);
			System.out.println("Current disc ID=" + disc.getId());
			DiscItem di = new DiscItem("FooItem2", "/nowhere/there", 1500L, disc);
			java.util.Map<String, String> m = new java.util.HashMap<String, String>();
			m.put("hossz", "15");
			m.put("mufaj", "pop");
			m.put("kulcs", "lyuk");
			di.setProperties(m);
			DiscItemDAO discItemDao = (DiscItemDAO)SpringUtil.getBean("discItemDao");
			discItemDao.save(di);
			return;
		}		
		if("load".equals(args[0])){
			DiscDAO discDao = (DiscDAO)SpringUtil.getBean("discDao");
			List<Disc> discs = discDao.findAll();
			
			for(Disc d : discs){
				System.out.println(d + "\n ---- ");
			}
			System.out.println("End of discs");
			DiscItemDAO discItemDao = (DiscItemDAO)SpringUtil.getBean("discItemDao");
			List<DiscItem> discItems = discItemDao.findAll();
			for(DiscItem di : discItems){
				System.out.println(di);
				System.out.println("Properties:");
				java.util.Iterator it = di.getPropertyIterator();
				while(it.hasNext()){
					java.util.Map.Entry<String, String> e = (java.util.Map.Entry<String, String>)it.next(); 
					System.out.println(e.getKey()+"="+e.getValue());
				}
			}
			
		}
		
		
	}

}
