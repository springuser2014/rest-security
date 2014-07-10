package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the date_format_locale database table.
 * 
 */
@Embeddable
public class DateFormatLocalePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false, length=64)
	private String type;

	@Column(unique=true, nullable=false, length=12)
	private String language;

	public DateFormatLocalePK() {
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DateFormatLocalePK)) {
			return false;
		}
		DateFormatLocalePK castOther = (DateFormatLocalePK)other;
		return 
			this.type.equals(castOther.type)
			&& this.language.equals(castOther.language);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.type.hashCode();
		hash = hash * prime + this.language.hashCode();
		
		return hash;
	}
}