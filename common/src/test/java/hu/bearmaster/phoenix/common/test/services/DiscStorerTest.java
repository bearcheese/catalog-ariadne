package hu.bearmaster.phoenix.common.test.services;

import hu.bearmaster.phoenix.common.dao.CategoryDAO;
import hu.bearmaster.phoenix.common.dao.TypeDAO;
import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.common.model.Type;
import hu.bearmaster.phoenix.common.services.PersistenceService;
import hu.bearmaster.phoenix.common.services.FileManagementService;
import hu.bearmaster.phoenix.common.util.Constants;
import hu.bearmaster.phoenix.common.util.SpringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class DiscStorerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//System.setProperty(SpringUtil.SPRING_XML, "spring-ariadne-hsqldb.xml");
		
		FileManagementService fileManager = (FileManagementService)SpringUtil.getBean(Constants.FILE_MANAGEMENT_BEAN);
		PersistenceService dbManager = (PersistenceService)SpringUtil.getBean(Constants.DB_MANAGEMENT_BEAN);
		
		//File root = new File("D:\\");
		
		ScanResult discEntry = fileManager.scanDisc("J:\\");
		
		Disc disc = discEntry.getDisc();
		
		BufferedReader bf;
		
		
		
		
		
		bf = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("A lemez neve: ");
			disc.setName(bf.readLine());
			System.out.print("A lemez kategoriaja: ");
			String catName = bf.readLine();
			Category cat = dbManager.findCategoryByName(catName);
			if ( cat == null ) {
				cat = new Category(catName, "Dummy desc for " + catName);
				dbManager.addNewCategory(cat);
			}
			disc.setCategory(cat);
			System.out.print("A lemez tipusa: ");
			String typeName = bf.readLine();
			Type type = dbManager.findTypeByName(typeName);
			if ( type == null ) {
				type = new Type(typeName);
				dbManager.addNewType(type);
			}
			disc.setType(type);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//a teljes lemez mentése
		
		dbManager.storeEntireDisc(discEntry);
		
		System.out.println("Mentés sikeresen befejeződött!");
	}

}
