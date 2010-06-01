package hu.bearmaster.phoenix.common.test;

import hu.bearmaster.phoenix.common.filewalker.FileWalker;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.util.SizeFormatter;

import java.io.File;

public class WalkerTest {

	public static void main(String[] args) {
		int mb = 1024*1024;
		
		FileWalker fw = new FileWalker("J:\\");
		//FileWalker fw = new FileWalker(new File("D:\\"), 0, new MP3Filter(), null);
		fw.scan();
		Disc d = fw.getDisc();
		
		System.out.println("Name = "+d.getName());
		System.out.println("Volume name = "+d.getVolumeName());
		System.out.println("Category = "+d.getCategory());
		System.out.println("Size = "+SizeFormatter.getDoubleDynamicSuffix(d.getSize()));
		System.out.println("Type = "+d.getType());
		System.out.println("Created = "+d.getCreated());
		System.out.println("Comment = "+d.getComment());
		
		for(DiscItem di : fw.getDiscItems()){
			System.out.println(di.getPath()+"/"+di.getName()+ " s="+SizeFormatter.getDoubleDynamicSuffix(di.getLength()));
		}
		System.out.println("Statistics:");
		java.util.Map<String, Object> hm = fw.getStatistics();
		for(String s : hm.keySet()){
			System.out.println(s+":"+hm.get(s));
			if ("totalSize".equals(s)) {
				System.out.println("Dynamic suffix: " + SizeFormatter.getDynamicSuffix((Long)hm.get(s)));
				System.out.println("Double dynamic suffix: " + SizeFormatter.getDoubleDynamicSuffix((Long)hm.get(s)));
			}
		}
	}
}

class MP3Filter implements java.io.FileFilter{

	public boolean accept(File f) {
		if(f.isDirectory() || f.getName().endsWith(".mp3")) return true;
		return false;
	}
	
}
