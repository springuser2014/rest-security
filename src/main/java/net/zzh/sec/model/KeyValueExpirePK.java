package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the key_value_expire database table.
 * 
 */
@Embeddable
public class KeyValueExpirePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String collection;

	@Column(insertable=false, updatable=false)
	private String name;

	public KeyValueExpirePK() {
	}
	public String getCollection() {
		return this.collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KeyValueExpirePK)) {
			return false;
		}
		KeyValueExpirePK castOther = (KeyValueExpirePK)other;
		return 
			this.collection.equals(castOther.collection)
			&& this.name.equals(castOther.name);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.collection.hashCode();
		hash = hash * prime + this.name.hashCode();
		
		return hash;
	}
}