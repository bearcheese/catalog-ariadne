package hu.bearmaster.phoenix.common.test.dao;

import hu.bearmaster.phoenix.common.dao.DiscDAO;
import hu.bearmaster.phoenix.common.dao.DiscItemDAO;
import hu.bearmaster.phoenix.common.filewalker.FileWalker;
import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.model.Type;
import hu.bearmaster.phoenix.common.util.SpringUtil;

import java.io.*;



public class StoreDiscSample {

	/**
	 * @param args - a letapogatásra kerülő meghajtó elérési útja
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: "+StoreDiscSample.class.getName()+" <root_path>");
			return;
		}
		
		File root = new File(args[0]);
		FileWalker fw = new FileWalker(root);
		
		fw.scan();
		
		Disc disc = fw.getDisc();
		
		//További szükséges adatok bekérése
		BufferedReader bf;
		
		bf = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("A lemez neve: ");
			disc.setName(bf.readLine());
			System.out.print("A lemez kategoriaja: ");
			disc.setCategory(new Category(bf.readLine(), "dummy"));
			System.out.print("A lemez tipusa: ");
			disc.setType(new Type(bf.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//lemez mentése
		DiscDAO discDao = (DiscDAO)SpringUtil.getBean("discDao");
		discDao.save(disc);
		
		//a lemezen lévő elemek mentése
		DiscItemDAO discItemDao = (DiscItemDAO)SpringUtil.getBean("discItemDao");
		for(DiscItem di : fw.getDiscItems()){
			di.setDisc(disc);
			discItemDao.save(di);
		}
		
		
	}

}
