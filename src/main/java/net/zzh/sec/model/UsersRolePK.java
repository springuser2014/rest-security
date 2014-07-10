package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the users_roles database table.
 * 
 */
@Embeddable
public class UsersRolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int uid;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int rid;

	public UsersRolePK() {
	}
	public int getUid() {
		return this.uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRid() {
		return this.rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsersRolePK)) {
			return false;
		}
		UsersRolePK castOther = (UsersRolePK)other;
		return 
			(this.uid == castOther.uid)
			&& (this.rid == castOther.rid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.uid;
		hash = hash * prime + this.rid;
		
		return hash;
	}
}