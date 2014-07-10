package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the authmap database table.
 * 
 */
@Entity
@Table(name="authmap")
@NamedQuery(name="Authmap.findAll", query="SELECT a FROM Authmap a")
public class Authmap implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int aid;

	@Column(nullable=false, length=128)
	private String authname;

	@Column(nullable=false, length=128)
	private String module;

	@Column(nullable=false)
	private int uid;

	public Authmap() {
	}

	public int getAid() {
		return this.aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getAuthname() {
		return this.authname;
	}

	public void setAuthname(String authname) {
		this.authname = authname;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

}