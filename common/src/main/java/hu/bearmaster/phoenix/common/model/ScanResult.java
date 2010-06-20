package hu.bearmaster.phoenix.common.model;

import java.util.List;
import java.util.Map;


/**
 * This is a helper business object class, for storing the results of drive scanning.
 * The disc and the disc items can be persisted after scanning.
 * @author "Zoltan Molnar"
 *
 */
public class ScanResult {
	
	private String scanRoot;
	private Disc disc;
	private List<DiscItem> discItems;
	private Map<String, Object> statistics;
	
	/**
	 * Default constructor for creating an empty ScanResult object
	 */
	public ScanResult() {
		//default constructor
	}
	
	/**
	 * @param disc
	 * @param discItems
	 */
	public ScanResult(final String scanRoot, final Disc disc, final List<DiscItem> discItems, final Map<String, Object> statistics) {
		super();
		this.scanRoot = scanRoot;
		this.disc = disc;
		this.discItems = discItems;
		this.statistics = statistics;
	}
	
	public Disc getDisc() {
		return disc;
	}

	public List<DiscItem> getDiscItems() {
		return discItems;
	}

	public void setDisc(final Disc disc) {
		this.disc = disc;
	}

	public void setDiscItems(final List<DiscItem> discItems) {
		this.discItems = discItems;
	}

	public Map<String, Object> getStatistics() {
		return statistics;
	}

	public void setStatistics(final Map<String, Object> statistics) {
		this.statistics = statistics;
	}

	public String getScanRoot() {
		return scanRoot;
	}

	public void setScanRoot(String scanRoot) {
		this.scanRoot = scanRoot;
	}
	
}
