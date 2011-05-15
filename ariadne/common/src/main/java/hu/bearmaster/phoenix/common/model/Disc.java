/**
 * Lemez osztály (Disc):
 * Tulajdonságai:
 * - Azonosító (id) - hosszú egész
 * - Név (name)
 * - Kötetnév (volumename)
 * - Kategória: felhasználó által megadott kategória besorolás (category)
 * - Méret (size): származtatott adat (konstruktorban nem szerepelhet!)
 * - Típus (type): a kötet típusa, a felhasználó által állítható (cd,dvd,floppy,merevlemez)
 * - Rögzítés dátuma (created)
 * - Megjegyzés (comment): a lemezhez fűzött szöveges megjegyzés
 */
package hu.bearmaster.phoenix.common.model;

import java.sql.Date;
/**
 * @author MZx
 *
 */
public class Disc{
	
	private Long id;
	private String name;
	private String volumeName;
	private Category category;
	private long size;
	private Type type;
	private Date created;
	private String comment;
	
	public Disc(){
		//default constructor for Hibernate
	}
	
	/**
	 * @param name
	 * @param volumeName
	 * @param category
	 * @param type
	 * @param created
	 * @param comment
	 */
	public Disc(final String name, 
			final String volumeName, 
			final Category category, 
			final Type type, 
			final Date created, 
			final String comment) {
		super();
		this.name = name;
		this.volumeName = volumeName;
		this.category = category;
		this.type = type;
		this.created = created;
		this.comment = comment;
	}

	/**
	 * @param name
	 * @param volumeName
	 * @param category
	 * @param type
	 * @param created
	 */
	public Disc(final String name, 
			final String volumeName, 
			final Category category, 
			final Type type, 
			final Date created) {
		this(name, volumeName, category, type, created, "");
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	private void setCreated(Date created) {
		this.created = created;
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
	private void setId(Long id) {
		this.id = id;
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
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}
	
	private void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the volumeName
	 */
	public String getVolumeName() {
		return volumeName;
	}

	/**
	 * @param volumeName the volumeName to set
	 */
	private void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	
	public String toString(){
		return name+ "("+volumeName+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((volumeName == null) ? 0 : volumeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disc other = (Disc) obj;
		if (volumeName == null) {
			if (other.volumeName != null)
				return false;
		} else if (!volumeName.equals(other.volumeName))
			return false;
		return true;
	}
	
	
}
