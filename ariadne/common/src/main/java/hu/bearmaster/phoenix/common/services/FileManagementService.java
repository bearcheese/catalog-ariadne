package hu.bearmaster.phoenix.common.services;

import hu.bearmaster.phoenix.common.filewalker.PropertyCollector;
import hu.bearmaster.phoenix.common.model.ScanResult;

import java.io.FileFilter;
import java.util.List;



/**
 * This service's responsibility to collect information from the discs and other storage
 * devices using the filewalker classes and applying additional filters etc.
 * @author Zoltan_Molnar1
 *
 */
public interface FileManagementService {
	
	/**
	 * Returns a ScanResult object created based upon the given path parameter, and contains
	 * the result disc and discitem information
	 * @param path a valid absolute path in the current file system
	 * @return a ScanResult object representing the collected file data
	 */
	ScanResult scanDisc(String path);
	
	/**
	 * Return a ScanResult object created based upon the given path parameter, and filtered by the 
	 * given FileFilter instance.
	 * @param path a valid absolute path in the current file system
	 * @param filter a FileFilter instance, which determines what type of files will be included in the scan
	 * @return a ScanResult object representing the collected file data
	 */
	ScanResult scanDisc(String path, FileFilter filter);
	
	/**
	 * Return a ScanResult object created based upon the given path parameter, and filtered by the 
	 * given FileFilter instance.
	 * @param path a valid absolute path in the current file system
	 * @param filter a FileFilter instance, which determines what type of files will be included in the scan
	 * @param propertyCollector this instance will collect additional data during the scan, and it will be added to the Disck information
	 * @return a ScanResult object representing the collected file data
	 */
	ScanResult scanDisc(String path, FileFilter filter, List<PropertyCollector> propertyCollector);

}
