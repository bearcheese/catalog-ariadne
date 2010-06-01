package hu.bearmaster.phoenix.common.test;

import java.io.*;

import javax.swing.filechooser.*;

public class Volume {

	 public static void main(String[] args) {
		File[] v = File.listRoots();
		for (int i = 1; i < v.length; i++) {
			System.out.println(v[i]+": \"" + get(v[i].getAbsolutePath()) + "\"");
		}
		System.out.println(new java.util.GregorianCalendar().getTime());
		
		try {
			//Process p = Runtime.getRuntime().exec("dir");
			Process p = Runtime.getRuntime().exec("cmd.exe /C vol J:");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			while ((s = br.readLine()) != null){
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

	 public static String get(String path) {
	  FileSystemView view = FileSystemView.getFileSystemView();
	  File dir = new File(path);
	  String name = view.getSystemDisplayName(dir);
	  //System.out.println(view.isDrive(dir));
	  System.out.println(view.getSystemTypeDescription(dir));
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
