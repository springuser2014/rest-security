package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the watchdog database table.
 * 
 */
@Embeddable
public class WatchdogPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int wid;

	private int uid;

	public WatchdogPK() {
	}
	public int getWid() {
		return this.wid;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public int getUid() {
		return this.uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WatchdogPK)) {
			return false;
		}
		WatchdogPK castOther = (WatchdogPK)other;
		return 
			(this.wid == castOther.wid)
			&& (this.uid == castOther.uid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.wid;
		hash = hash * prime + this.uid;
		
		return hash;
	}
}