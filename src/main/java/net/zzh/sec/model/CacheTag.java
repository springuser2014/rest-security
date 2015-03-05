package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cache_tags database table.
 * 
 */
@Entity
@Table(name="cache_tags")
@NamedQuery(name="CacheTag.findAll", query="SELECT c FROM CacheTag c")
public class CacheTag implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String tag;

	private int deletions;

	private int invalidations;

	public CacheTag() {
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getDeletions() {
		return this.deletions;
	}

	public void setDeletions(int deletions) {
		this.deletions = deletions;
	}

	public int getInvalidations() {
		return this.invalidations;
	}

	public void setInvalidations(int invalidations) {
		this.invalidations = invalidations;
	}

}