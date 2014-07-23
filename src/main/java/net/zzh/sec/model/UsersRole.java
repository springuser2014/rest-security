package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the users_roles database table.
 * 
 */
@Entity
@Table(name="users_roles")
@NamedQuery(name="UsersRole.findAll", query="SELECT u FROM UsersRole u")
public class UsersRole implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsersRolePK id;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="uid", nullable=false, insertable=false, updatable=false)
	private User user;

	public UsersRole() {
	}

	public UsersRolePK getId() {
		return this.id;
	}

	public void setId(UsersRolePK id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}