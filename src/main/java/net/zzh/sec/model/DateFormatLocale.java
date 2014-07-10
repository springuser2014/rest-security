package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the date_format_locale database table.
 * 
 */
@Entity
@Table(name="date_format_locale")
@NamedQuery(name="DateFormatLocale.findAll", query="SELECT d FROM DateFormatLocale d")
public class DateFormatLocale implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DateFormatLocalePK id;

	@Column(nullable=false, length=100)
	private String format;

	public DateFormatLocale() {
	}

	public DateFormatLocalePK getId() {
		return this.id;
	}

	public void setId(DateFormatLocalePK id) {
		this.id = id;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}