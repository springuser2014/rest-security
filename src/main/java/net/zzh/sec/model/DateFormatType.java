package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the date_format_type database table.
 * 
 */
@Entity
@Table(name="date_format_type")
@NamedQuery(name="DateFormatType.findAll", query="SELECT d FROM DateFormatType d")
public class DateFormatType implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=64)
	private String type;

	@Column(nullable=false)
	private byte locked;

	@Column(nullable=false, length=255)
	private String title;

	public DateFormatType() {
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte getLocked() {
		return this.locked;
	}

	public void setLocked(byte locked) {
		this.locked = locked;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}