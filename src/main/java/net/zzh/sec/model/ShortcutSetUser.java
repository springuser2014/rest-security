package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the shortcut_set_users database table.
 * 
 */
@Entity
@Table(name="shortcut_set_users")
@NamedQuery(name="ShortcutSetUser.findAll", query="SELECT s FROM ShortcutSetUser s")
public class ShortcutSetUser implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int uid;

	@Column(name="set_name")
	private String setName;

	//bi-directional one-to-one association to Users
	@OneToOne
	@JoinColumn(name="uid")
	private Users user;

	public ShortcutSetUser() {
	}

	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getSetName() {
		return this.setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}