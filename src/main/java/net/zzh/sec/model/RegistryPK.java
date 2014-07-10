package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the registry database table.
 * 
 */
@Embeddable
public class RegistryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false, length=255)
	private String name;

	@Column(unique=true, nullable=false, length=9)
	private String type;

	public RegistryPK() {
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RegistryPK)) {
			return false;
		}
		RegistryPK castOther = (RegistryPK)other;
		return 
			this.name.equals(castOther.name)
			&& this.type.equals(castOther.type);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.name.hashCode();
		hash = hash * prime + this.type.hashCode();
		
		return hash;
	}
}