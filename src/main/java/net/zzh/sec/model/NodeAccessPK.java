package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the node_access database table.
 * 
 */
@Embeddable
public class NodeAccessPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="entity_id", insertable=false, updatable=false)
	private int entityId;

	private int gid;

	private String realm;

	private String langcode;

	public NodeAccessPK() {
	}
	public int getEntityId() {
		return this.entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public int getGid() {
		return this.gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getRealm() {
		return this.realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}
	public String getLangcode() {
		return this.langcode;
	}
	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NodeAccessPK)) {
			return false;
		}
		NodeAccessPK castOther = (NodeAccessPK)other;
		return 
			(this.entityId == castOther.entityId)
			&& (this.gid == castOther.gid)
			&& this.realm.equals(castOther.realm)
			&& this.langcode.equals(castOther.langcode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.entityId;
		hash = hash * prime + this.gid;
		hash = hash * prime + this.realm.hashCode();
		hash = hash * prime + this.langcode.hashCode();
		
		return hash;
	}
}