package hu.bearmaster.phoenix.common.test;

import java.io.*;

import javax.swing.filechooser.*;

public class Volume {

	public static FileSystemView fsv = FileSystemView.getFileSystemView();
	
	 public static void main(String[] args) {
		File[] v = fsv.getRoots();//File.listRoots();
		System.out.println("Length: " + v.length);
		
		for (int i = 0; i < v.length; i++) {
			System.out.println(fsv.getSystemDisplayName(v[i])+" \"" + get(v[i]) + "\"");
		}
		File desktop = v[0];
		File[] children = desktop.listFiles();
		for (File c : children) {
			if (c.isDirectory()) {
				System.out.println(" * " + fsv.getSystemDisplayName(c)+" \"" + get(c) + "\"\n");
				try {
					System.out.println(c.getCanonicalPath());
				} catch (IOException e) {
					System.out.println("No canonical name");
				}
				System.out.println(c.getAbsolutePath());
			}
			
		}
		System.out.println(new java.util.GregorianCalendar().getTime());
		//File file = new File("c:/temp/AnyName.{20D04FE0-3AEA-1069-A2D8-08002B30309D}");
		String osName = System.getProperty("os.name");
		System.out.println("*** OS Name: " + osName);
		try {
			//Process p = Runtime.getRuntime().exec("dir");
			Process p = Runtime.getRuntime().exec("cmd.exe /C vol G:");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			while ((s = br.readLine()) != null){
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

	 public static String get(File dir) {
	  String name = fsv.getSystemDisplayName(dir);
	  //System.out.println(view.isDrive(dir));
	  System.out.println(fsv.getSystemTypeDescription(dir));
	  if (name == null) { return null; }
	  name = name.trim();
	  if (name == null || name.length() < 1) {
	   return null;
	  }
	  int index = name.lastIndexOf(" (");
	  if (index > 0) {
	    name = name.substring(0, index);
	   }
	   return name;
	 }


}
