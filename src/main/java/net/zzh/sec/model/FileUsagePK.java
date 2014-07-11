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

	@Column(unique=true, nullable=false)
	private int id;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int fid;

	@Column(unique=true, nullable=false, length=64)
	private String type;

	@Column(unique=true, nullable=false, length=255)
	private String module;

	public FileUsagePK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
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
			(this.id == castOther.id)
			&& (this.fid == castOther.fid)
			&& this.type.equals(castOther.type)
			&& this.module.equals(castOther.module);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.fid;
		hash = hash * prime + this.type.hashCode();
		hash = hash * prime + this.module.hashCode();
		
		return hash;
	}
}