package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the key_value database table.
 * 
 */
@Entity
@Table(name="key_value")
@NamedQuery(name="KeyValue.findAll", query="SELECT k FROM KeyValue k")
public class KeyValue implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KeyValuePK id;

	private Object value;

	//bi-directional one-to-one association to KeyValueExpire
	@OneToOne(mappedBy="keyValue")
	private KeyValueExpire keyValueExpire;

	public KeyValue() {
	}

	public KeyValuePK getId() {
		return this.id;
	}

	public void setId(KeyValuePK id) {
		this.id = id;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public KeyValueExpire getKeyValueExpire() {
		return this.keyValueExpire;
	}

	public void setKeyValueExpire(KeyValueExpire keyValueExpire) {
		this.keyValueExpire = keyValueExpire;
	}

}