package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the semaphore database table.
 * 
 */
@Entity
@Table(name="semaphore")
@NamedQuery(name="Semaphore.findAll", query="SELECT s FROM Semaphore s")
public class Semaphore implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String name;

	private double expire;

	private String value;

	public Semaphore() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getExpire() {
		return this.expire;
	}

	public void setExpire(double expire) {
		this.expire = expire;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}