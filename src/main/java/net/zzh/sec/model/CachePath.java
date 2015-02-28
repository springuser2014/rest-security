package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cache_path database table.
 * 
 */
@Entity
@Table(name="cache_path")
@NamedQuery(name="CachePath.findAll", query="SELECT c FROM CachePath c")
public class CachePath implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String cid;

	private int created;

	private Object data;

	private int expire;

	private short serialized;

	public CachePath() {
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