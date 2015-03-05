package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the users_data database table.
 * 
 */
@Entity
@Table(name="users_data")
@NamedQuery(name="UsersData.findAll", query="SELECT u FROM UsersData u")
public class UsersData implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsersDataPK id;

	private byte serialized;

	private Object value;

	//bi-directional many-to-one association to Users
	@ManyToOne
	@JoinColumn(name="uid")
	private Users user;

	public UsersData() {
	}

	public UsersDataPK getId() {
		return this.id;
	}

	public void setId(UsersDataPK id) {
		this.id = id;
	}

	public byte getSerialized() {
		return this.serialized;
	}

	public void setSerialized(byte serialized) {
		this.serialized = serialized;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}