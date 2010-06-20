package hu.bearmaster.phoenix.common.filewalker;

import hu.bearmaster.phoenix.common.model.Category;
import hu.bearmaster.phoenix.common.model.Disc;
import hu.bearmaster.phoenix.common.model.DiscItem;
import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.common.model.Type;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

/**
 * FileWalker class
 * This helper class collects information from files within a given directory structure. Creates a {@link ScanResult} object as result,
 * which can store a {@link Disc} object with general information about the  drive itself which contains the directory structure, and 
 * also stores a list of {@link DiscItem} objects, each of them represents a file found during the scanning. FileWalker accepts parameters,
 * which can control the scanning: what is the maximum depth of scanning, which files should be processed (given by a {@link FileFilter} object),
 * and objects called property collectors (an imlementors of {@link PropertyCollector} interface), which collects file specific information about the 
 * files during scanning. These informations will be stored in the DiscItem objects as well.
 * 
 * @author "Zoltan Molnar"
 *
 */
public class FileWalker {
	private File root; //root directory of scanning (mostly a drive's root directory)
	private int depth = 0; //the maximum depth to scan the filesystem
	private int actDepth;
	private FileFilter filter; //filters out what files will be scanned and stored in result object
	private List<PropertyCollector> collectors;
	
	//Statistics
	private int scannedFileNum = 0;
	private int scannedDirNum = 0;
	private long totalSize = 0;
		
	private Disc disc; //the scanned object
	private List<DiscItem> discItems; //item list of the disc

	/**
	 * Default filter which accepts every file
	 */
	public static final FileFilter ALLFILES;
	static {
		ALLFILES = new FileFilter(){
			public boolean accept(final File arg0) {
				return true;
			}			
		};
	}
	
	/**
	 * Constructs a FileWalker object which can scan recursively the given root directory.
	 * @param root a root directory of the scan
	 */
	public FileWalker(final File root){
		this(root, 0, null, null);
	}
	
	/**
	 * Constructs a FileWalker object which can scan recursively the given root directory.
	 * @param root a root directory of the scan
	 */
	public FileWalker(final String root){
		this(new File(root));
	}
	
	/**
	 * Constructs a FileWalker object, which can scan the given root directory, and will only
	 * collect files, which are allowed by the given filter, and also collects other information
	 * during the scan by using the given collector instance.
	 * @param root the root directory of the scan
	 * @param filter for filtering the scanned files
	 * @param collectors for collecting other file informations
	 */
	public FileWalker(final File root, final java.io.FileFilter filter, final List<PropertyCollector> collectors){
		this(root, 0, filter, collectors);
	}
	
	/**
	 * Constructs a FileWalker object, which can scan the given root directory, and will only
	 * collect files, which are allowed by the given filter, and also collects other information
	 * during the scan by using the given collector instance. Scans only those files and directories
	 * which are not deeper then the given depth value. If depth is 0 or negative then no depth
	 * check will be performed.
	 * @param root root directory of scanning
	 * @param depth max. depth of scanning
	 * @param filter file filter to control which files should be scanned
	 * @param collectors list of property collector
	 */
	public FileWalker(final File root, final int depth, final java.io.FileFilter filter, final List<PropertyCollector> collectors){
		this.root = root;
		this.depth = depth >= 0 ? depth : 0; //no negative depth value
		this.filter = filter;
		this.collectors = collectors;
	}
	
	public FileFilter getFilter(){
		return filter;
	}
	
	public void setFilter(final FileFilter filter){
		this.filter = filter;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public void setDepth(final int depth){
		this.depth = depth;
	}
	 
	public List<PropertyCollector> getCollectors() {
		return collectors;
	}

	public void setCollectors(final List<PropertyCollector> collectors) {
		this.collectors = collectors;
	}

	public void scan(){
		scanDiscInfo(); //gather drive info
		if (filter == null) {
			filter = ALLFILES;
		}
		discItems = new LinkedList<DiscItem>();
		actDepth = 0;
		scanFileSystem(root); //actual scanning
	}
	
	protected void scanDiscInfo(){
		String name = "DiscName";
		String volumeName = getVolumeName(root);
		Category category = new Category();
		Type type = new Type();
		java.sql.Date created = new java.sql.Date(new java.util.GregorianCalendar().getTimeInMillis());
		
		disc = new Disc(name, volumeName, category, type, created);
	}
	
	protected String getVolumeName(final File root){
		FileSystemView view = FileSystemView.getFileSystemView();
		String name = view.getSystemDisplayName(root);
		//System.out.println(view.isDrive(dir));
		//System.out.println(view.getSystemTypeDescription(dir));
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


	protected void scanFileSystem(final File dir){
		if ((depth != 0) && (actDepth > depth)){
			return;
		}
		File[] files = dir.listFiles(filter);
		actDepth++;
		for(File f : files){
			if(f.isFile()){
				scannedFileNum++;
				discItems.add(scanFileInfo(f));
			}
			else {
				scannedDirNum++;
				scanFileSystem(f);
			}
		}
	}
	
	protected DiscItem scanFileInfo(final File file){
		DiscItem discItem = new DiscItem(file);
		totalSize += discItem.getLength();
		if (collectors != null && !collectors.isEmpty()) {
			for (PropertyCollector pc : collectors) {
				 discItem.setProperties(pc.scanItem(file));
			}
		}
		return discItem;
	}
	
	public Disc getDisc(){
		return disc;
	}
	 
	public List<DiscItem> getDiscItems(){
		return discItems;
	}
	
	public Map<String, Object> getStatistics(){
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("scannedFileNum", scannedFileNum);
		hashmap.put("scannedDirNum", scannedDirNum);
		hashmap.put("totalSize", totalSize);
		return hashmap;
	}

	public ScanResult getResult() {
		return new ScanResult(root.getAbsolutePath(), disc, discItems, getStatistics());
	}
	
}
