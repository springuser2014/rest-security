package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the registry database table.
 * 
 */
@Entity
@Table(name="registry")
@NamedQuery(name="Registry.findAll", query="SELECT r FROM Registry r")
public class Registry implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RegistryPK id;

	@Column(nullable=false, length=255)
	private String module;

	@Column(nullable=false)
	private int weight;

	//bi-directional many-to-one association to RegistryFile
	@ManyToOne
	@JoinColumn(name="filename", nullable=false)
	private RegistryFile registryFile;

	public Registry() {
	}

	public RegistryPK getId() {
		return this.id;
	}

	public void setId(RegistryPK id) {
		this.id = id;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public RegistryFile getRegistryFile() {
		return this.registryFile;
	}

	public void setRegistryFile(RegistryFile registryFile) {
		this.registryFile = registryFile;
	}

}