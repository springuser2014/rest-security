package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the file_usage database table.
 * 
 */
@Embeddable
public class FileUsagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int fid;

	private String type;

	private String id;

	private String module;

	public FileUsagePK() {
	}
	public int getFid() {
		return this.fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModule() {
		return this.module;
	}
	public void setModule(String module) {
		this.module = module;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FileUsagePK)) {
			return false;
		}
		FileUsagePK castOther = (FileUsagePK)other;
		return 
			(this.fid == castOther.fid)
			&& this.type.equals(castOther.type)
			&& this.id.equals(castOther.id)
			&& this.module.equals(castOther.module);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fid;
		hash = hash * prime + this.type.hashCode();
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.module.hashCode();
		
		return hash;
	}
}