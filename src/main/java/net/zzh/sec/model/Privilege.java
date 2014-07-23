package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the privilege database table.
 * 
 */
@Entity
@Table(name="privilege")
@NamedQuery(name="Privilege.findAll", query="SELECT p FROM Privilege p")
public class Privilege implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="priv_id", unique=true, nullable=false)
	private String privId;

	@Column(length=255)
	private String description;

	@Column(nullable=false, length=255)
	private String name;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="role_privileges"
		, joinColumns={
			@JoinColumn(name="priv_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="rid", nullable=false)
			}
		)
	private List<Role> roles;

	public Privilege() {
	}

	public String getPrivId() {
		return this.privId;
	}

	public void setPrivId(String privId) {
		this.privId = privId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}