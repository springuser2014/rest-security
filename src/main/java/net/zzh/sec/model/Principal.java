package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the principal database table.
 * 
 */
@Entity
@Table(name="principal")
@NamedQuery(name="Principal.findAll", query="SELECT p FROM Principal p")
public class Principal implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="principal_id", unique=true, nullable=false)
	private String principalId;

	private byte locked;

	@Column(nullable=false, length=255)
	private String name;

	@Column(nullable=false, length=255)
	private String password;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="principal_roles"
		, joinColumns={
			@JoinColumn(name="principal_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="rid", nullable=false)
			}
		)
	private List<Role> roles;

	public Principal() {
	}

	public String getPrincipalId() {
		return this.principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public byte getLocked() {
		return this.locked;
	}

	public void setLocked(byte locked) {
		this.locked = locked;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}