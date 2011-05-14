package hu.bearmaster.phoenix.common.services.impl;

import hu.bearmaster.phoenix.common.filewalker.FileWalker;
import hu.bearmaster.phoenix.common.filewalker.PropertyCollector;
import hu.bearmaster.phoenix.common.model.ScanResult;
import hu.bearmaster.phoenix.common.services.FileManagementService;

import java.io.File;
import java.io.FileFilter;
import java.util.List;


/**
 * Implementation of {@link FileManagementService} interface
 * @author "Zoltan Molnar"
 *
 */
public class FileManagementServiceImpl implements FileManagementService {
	
	/**
	 * Equivalent with <code>scan(path, FileWalker.ALLFILES, null)</code> call.
	 * @see FileManagementService#scanDisc(String)
	 */
	public ScanResult scanDisc(final String path) {
		return scanDisc(path, FileWalker.ALLFILES, null);
	}

	/**
	 * Equivalent with <code>scan(path, filter, null)</code> call.
	 * @see FileManagementService#scanDisc(String, FileFilter)
	 */
	public ScanResult scanDisc(final String path, final FileFilter filter) {
		return scanDisc(path, filter, null);
	}

	/**
	 * @see FileManagementService#scanDisc(String, FileFilter, PropertyCollector)
	 */
	public ScanResult scanDisc(final String path, final FileFilter filter,
			final List<PropertyCollector> propertyCollector) {
		final FileWalker walker = new FileWalker(new File(path), filter, propertyCollector);
		walker.scan();
		return walker.getResult();
	}

}
