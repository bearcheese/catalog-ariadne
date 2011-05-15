package hu.bearmaster.phoenix.common.test.dao;


import hu.bearmaster.phoenix.common.dao.DiscDAO;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.util.SizeFormatter;
import hu.bearmaster.phoenix.common.util.SpringUtil;

public class LoadDiscSample {
	
	static int mb = 1024*1024;
	
	public static void main(String[] args){
		if(args.length != 1) {
			System.out.println("Usage: "+LoadDiscSample.class.getName()+" <disc_id>");
			return;
		}
		DiscDAO discDao = (DiscDAO)SpringUtil.getBean("discDao");
		System.out.println("Input = '"+args[0]+"'");
		Long id = new Long(args[0]);
		System.out.println("Disc id is "+id);
		Disc d = discDao.findByID(id);
		System.out.println("Disc properties: ");
		System.out.println("Name = "+d.getName());
		System.out.println("Volume name = "+d.getVolumeName());
		System.out.println("Category = "+d.getCategory());
		System.out.println("Size = "+SizeFormatter.getDynamicSuffix(d.getSize()));
		System.out.println("Type = "+d.getType());
		System.out.println("Created = "+d.getCreated());
		System.out.println("Comment = "+d.getComment()+"\n-------------------\n");
		
		/*
		DiscItemDAO discItemDao = (DiscItemDAO)SpringUtil.getBean("discItemDao");
		
		List<DiscItem> discItems = discItemDao.findByDisc(d);
        
        for(DiscItem di : discItems){
        	System.out.println(di.getPath()+ "/" + di.getName()+ " s="+SizeFormatter.getDynamicSuffix(di.getLength()));
        }*/

	}
	
	
}
