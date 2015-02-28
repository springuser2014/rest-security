package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the history database table.
 * 
 */
@Embeddable
public class HistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int uid;

	private int nid;

	public HistoryPK() {
	}
	public int getUid() {
		return this.uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getNid() {
		return this.nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HistoryPK)) {
			return false;
		}
		HistoryPK castOther = (HistoryPK)other;
		return 
			(this.uid == castOther.uid)
			&& (this.nid == castOther.nid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.uid;
		hash = hash * prime + this.nid;
		
		return hash;
	}
}