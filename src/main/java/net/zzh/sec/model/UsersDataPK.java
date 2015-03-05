package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the users_data database table.
 * 
 */
@Embeddable
public class UsersDataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int uid;

	private String module;

	private String name;

	public UsersDataPK() {
	}
	public int getUid() {
		return this.uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getModule() {
		return this.module;
	}
	public void setModule(String module) {
		this.module = module;
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
		if (!(other instanceof UsersDataPK)) {
			return false;
		}
		UsersDataPK castOther = (UsersDataPK)other;
		return 
			(this.uid == castOther.uid)
			&& this.module.equals(castOther.module)
			&& this.name.equals(castOther.name);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.uid;
		hash = hash * prime + this.module.hashCode();
		hash = hash * prime + this.name.hashCode();
		
		return hash;
	}
}