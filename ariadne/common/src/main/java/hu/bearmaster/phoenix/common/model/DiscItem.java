/**
 * Lemezelem osztály (DiscItem):
 * Gyakorlatilag egy lemezen található fájlt reprezentáló osztály. A könyvtárak nem kerülnek 
 * tárolásra explicit módon (csak az elérési út)
 * Tulajdonságai:
 * - Azonosító (id) - hosszú egász
 * - Név (name): a reprezentált fájl neve (kiterjesztéssel együtt!)
 * - Elérési út (path)
 * - Méret (length): a fájl mérete bájtban mérve
 * - Lemezazonosító (discId): ezen a lemezen található az adott item
 * - Lemezelem tulajdonságok (DiscItemProperties): opcionálisan megadható objektum
 */
package hu.bearmaster.phoenix.common.model;


import java.io.File;
import java.util.*;
/**
 * @author MZx
 *
 */
public class DiscItem implements Comparable<DiscItem> {
	private Long id;
	private String name;
	private String path;
	private long length;
	private Disc disc;
	private Map<String, String> properties;
	
	public DiscItem(){
	}
	
	public DiscItem(String name, String path, long length, Disc disc) {
		super();
		this.name = name;
		this.path = path.replace('\\', '/');
		this.length = length;
		this.disc = disc;
	}
	
	public DiscItem(File file){
		this(file, null);
	}
	
	public DiscItem(File file, Disc disc){
		this(file.getName(), file.getParentFile().getAbsolutePath().substring(file.getParentFile().getAbsolutePath().indexOf(":\\")+1), file.length(), disc);
	}

	/**
	 * @return the discId
	 */
	public Disc getDisc() {
		return disc;
	}

	/**
	 * @param discId the discId to set
	 */
	public void setDisc(Disc disc) {
		this.disc = disc;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	void setLength(long length) {
		this.length = length;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	void setPath(String path) {
		this.path = path;
	}

	/**
	 * Használata kerálendá!
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}
	/**
	 * Ennek segátságável hozzáfárhetánk a tulajdonságokhoz, egy iterátor segátságável
	 * @return a tartalmazott tulajdonság kulcs-árták párjainak iterátora
	 */
	public Iterator<Map.Entry<String, String>> getPropertyIterator(){
		return properties.entrySet().iterator();
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public String getExtension(){
		return name.substring(name.lastIndexOf(".")+1);
	}

	public String toString(){
		return "("+id+") "+path+"/"+name+" L="+length + " D="+(disc != null ? disc.getId() : "n/a");
	}
	
	public List<ItemProperty> getPropertyList() {
		ArrayList<ItemProperty> propList = new ArrayList<ItemProperty>();
		
		for ( Map.Entry<String, String> entry : properties.entrySet() ) {
			propList.add(new ItemProperty(entry.getKey(), entry.getValue()));
		}
		
		return propList;
	}
	
	public void setPropertyList(List<ItemProperty> propList) {
		for ( ItemProperty property : propList ) {
			properties.put(property.getName(), property.getValue());
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 4441;
		int result = super.hashCode();
		result = PRIME * result + ((disc == null) ? 0 : disc.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final DiscItem other = (DiscItem) obj;
		if (disc == null) {
			if (other.disc != null)
				return false;
		} else if (!disc.equals(other.disc))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	public int compareTo(DiscItem other) {
		return this.name.compareTo(other.name);
	}	
}
