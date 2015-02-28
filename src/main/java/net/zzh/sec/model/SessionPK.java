package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the sessions database table.
 * 
 */
@Embeddable
public class SessionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String sid;

	private String ssid;

	public SessionPK() {
	}
	public String getSid() {
		return this.sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSsid() {
		return this.ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SessionPK)) {
			return false;
		}
		SessionPK castOther = (SessionPK)other;
		return 
			this.sid.equals(castOther.sid)
			&& this.ssid.equals(castOther.ssid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sid.hashCode();
		hash = hash * prime + this.ssid.hashCode();
		
		return hash;
	}
}