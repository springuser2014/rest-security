package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the key_value_expire database table.
 * 
 */
@Entity
@Table(name="key_value_expire")
@NamedQuery(name="KeyValueExpire.findAll", query="SELECT k FROM KeyValueExpire k")
public class KeyValueExpire implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KeyValueExpirePK id;

	private int expire;

	private Object value;

	//bi-directional one-to-one association to KeyValue
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="collection", referencedColumnName="collection"),
		@JoinColumn(name="name", referencedColumnName="name")
		})
	private KeyValue keyValue;

	public KeyValueExpire() {
	}

	public KeyValueExpirePK getId() {
		return this.id;
	}

	public void setId(KeyValueExpirePK id) {
		this.id = id;
	}

	public int getExpire() {
		return this.expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public KeyValue getKeyValue() {
		return this.keyValue;
	}

	public void setKeyValue(KeyValue keyValue) {
		this.keyValue = keyValue;
	}

}