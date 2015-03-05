package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the node_field_image database table.
 * 
 */
@Embeddable
public class NodeFieldImagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="entity_id", insertable=false, updatable=false)
	private int entityId;

	private byte deleted;

	private int delta;

	private String langcode;

	public NodeFieldImagePK() {
	}
	public int getEntityId() {
		return this.entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public byte getDeleted() {
		return this.deleted;
	}
	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}
	public int getDelta() {
		return this.delta;
	}
	public void setDelta(int delta) {
		this.delta = delta;
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
		if (!(other instanceof NodeFieldImagePK)) {
			return false;
		}
		NodeFieldImagePK castOther = (NodeFieldImagePK)other;
		return 
			(this.entityId == castOther.entityId)
			&& (this.deleted == castOther.deleted)
			&& (this.delta == castOther.delta)
			&& this.langcode.equals(castOther.langcode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.entityId;
		hash = hash * prime + ((int) this.deleted);
		hash = hash * prime + this.delta;
		hash = hash * prime + this.langcode.hashCode();
		
		return hash;
	}
}