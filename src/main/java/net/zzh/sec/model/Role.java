package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int rid;

	private String name;

	private int weight;

	//bi-directional one-to-one association to RolePermission
	@OneToOne(mappedBy="role")
	private RolePermission rolePermission;

	//bi-directional many-to-many association to Users
	@ManyToMany(mappedBy="roles")
	private List<Users> users;

	public Role() {
	}

	public int getRid() {
		return this.rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public RolePermission getRolePermission() {
		return this.rolePermission;
	}

	public void setRolePermission(RolePermission rolePermission) {
		this.rolePermission = rolePermission;
	}

	public List<Users> getUsers() {
		return this.users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

}