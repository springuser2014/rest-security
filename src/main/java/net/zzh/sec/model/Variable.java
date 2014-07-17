package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the variable database table.
 * 
 */
@Entity
@Table(name="variable")
@NamedQuery(name="Variable.findAll", query="SELECT v FROM Variable v")
public class Variable implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=128)
	private String name;

	@Lob
	@Column(nullable=false)
	private byte[] value;

	public Variable() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getValue() {
		return this.value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

}