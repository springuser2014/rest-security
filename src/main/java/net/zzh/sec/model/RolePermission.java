package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role_permission database table.
 * 
 */
@Entity
@Table(name="role_permission")
@NamedQuery(name="RolePermission.findAll", query="SELECT r FROM RolePermission r")
public class RolePermission implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolePermissionPK id;

	@Column(nullable=false, length=255)
	private String module;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="rid", nullable=false, insertable=false, updatable=false)
	private Role role;

	public RolePermission() {
	}

	public RolePermissionPK getId() {
		return this.id;
	}

	public void setId(RolePermissionPK id) {
		this.id = id;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}