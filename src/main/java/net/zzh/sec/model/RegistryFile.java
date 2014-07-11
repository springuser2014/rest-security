package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the registry_file database table.
 * 
 */
@Entity
@Table(name="registry_file")
@NamedQuery(name="RegistryFile.findAll", query="SELECT r FROM RegistryFile r")
public class RegistryFile implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=255)
	private String filename;

	@Column(nullable=false, length=64)
	private String hash;

	//bi-directional many-to-one association to Registry
	@OneToMany(mappedBy="registryFile")
	private List<Registry> registries;

	public RegistryFile() {
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public List<Registry> getRegistries() {
		return this.registries;
	}

	public void setRegistries(List<Registry> registries) {
		this.registries = registries;
	}

	public Registry addRegistry(Registry registry) {
		getRegistries().add(registry);
		registry.setRegistryFile(this);

		return registry;
	}

	public Registry removeRegistry(Registry registry) {
		getRegistries().remove(registry);
		registry.setRegistryFile(null);

		return registry;
	}

}