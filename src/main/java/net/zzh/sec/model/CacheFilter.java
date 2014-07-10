package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cache_filter database table.
 * 
 */
@Entity
@Table(name="cache_filter")
@NamedQuery(name="CacheFilter.findAll", query="SELECT c FROM CacheFilter c")
public class CacheFilter implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=255)
	private String cid;

	@Column(nullable=false)
	private int created;

	private Object data;

	@Column(nullable=false)
	private int expire;

	@Column(nullable=false)
	private short serialized;

	public CacheFilter() {
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

}