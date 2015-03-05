package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cache database table.
 * 
 */
@Entity
@Table(name="cache")
@NamedQuery(name="Cache.findAll", query="SELECT c FROM Cache c")
public class Cache implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String cid;

	@Column(name="checksum_deletions")
	private int checksumDeletions;

	@Column(name="checksum_invalidations")
	private int checksumInvalidations;

	private int created;

	private Object data;

	private int expire;

	private short serialized;

	private Object tags;

	public Cache() {
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getChecksumDeletions() {
		return this.checksumDeletions;
	}

	public void setChecksumDeletions(int checksumDeletions) {
		this.checksumDeletions = checksumDeletions;
	}

	public int getChecksumInvalidations() {
		return this.checksumInvalidations;
	}

	public void setChecksumInvalidations(int checksumInvalidations) {
		this.checksumInvalidations = checksumInvalidations;
	}

	public int getCreated() {
		return this.created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getExpire() {
		return this.expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public short getSerialized() {
		return this.serialized;
	}

	public void setSerialized(short serialized) {
		this.serialized = serialized;
	}

	public Object getTags() {
		return this.tags;
	}

	public void setTags(Object tags) {
		this.tags = tags;
	}

}