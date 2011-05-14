package hu.bearmaster.phoenix.common.test.services;

import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.services.PersistenceService;
import hu.bearmaster.phoenix.common.util.Constants;
import hu.bearmaster.phoenix.common.util.SizeFormatter;
import hu.bearmaster.phoenix.common.util.SpringUtil;

import java.util.List;




public class DiscLoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.setProperty(SpringUtil.SPRING_XML, "spring-ariadne-hsqldb.xml");
		//System.setProperty(SpringUtil.SPRING_CONTEXT_PATH, SpringUtil.PRIMARY_CONTEXT_PATH);
		PersistenceService dbserv = (PersistenceService) SpringUtil.getBean(Constants.DB_MANAGEMENT_BEAN);
		
		List<Disc> discs = dbserv.getAllDiscs();
		
		for(Disc d : discs){
			printDiscInfo(d);
		}

	}

	private static void printDiscInfo(Disc d) {
		System.out.println("Disc id is "+d.getId());
		System.out.println("Disc properties: ");
		System.out.println("Name = "+d.getName());
		System.out.println("Volume name = "+d.getVolumeName());
		System.out.println("Category = "+d.getCategory());
		System.out.println("Size = "+SizeFormatter.getDoubleDynamicSuffix(d.getSize()));
		System.out.println("Type = "+d.getType());
		System.out.println("Created = "+d.getCreated());
		System.out.println("Comment = "+d.getComment()+"\n-------------------\n");		
	}

}
