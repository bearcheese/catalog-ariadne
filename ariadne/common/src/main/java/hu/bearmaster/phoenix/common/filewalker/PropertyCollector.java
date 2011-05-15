package hu.bearmaster.phoenix.common.filewalker;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * This interface defines the necessary methods used by {@link FileWalker} during the file scan.
 * The implementors are able to analyze the content of the given file, and collect informations
 * about it, which can be persisted.
 * @author "Zoltan Molnar"
 *
 */
public interface PropertyCollector {
	
	/**
	 * A megkapott fájlból típusspecifikus információkat gyűjt, melyeket a visszaadott 
	 * Map<String, String> objektumban helyez el.
	 * @param item
	 * @return
	 */
	Map<String, String> scanItem(File item);
	
	/**
	 * Returns the list of the file extensions which are interested by this collector.
	 * This method is introduced to allow FileWalker to improve the efficiency of scanning.
	 * @return list of extensions
	 */
	List<String> getSupportedExtensions();

}
