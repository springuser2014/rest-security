package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the date_formats database table.
 * 
 */
@Entity
@Table(name="date_formats")
@NamedQuery(name="DateFormat.findAll", query="SELECT d FROM DateFormat d")
public class DateFormat implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int dfid;

	@Column(nullable=false, length=100)
	private String format;

	@Column(nullable=false)
	private byte locked;

	@Column(nullable=false, length=64)
	private String type;

	public DateFormat() {
	}

	public int getDfid() {
		return this.dfid;
	}

	public void setDfid(int dfid) {
		this.dfid = dfid;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public byte getLocked() {
		return this.locked;
	}

	public void setLocked(byte locked) {
		this.locked = locked;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}