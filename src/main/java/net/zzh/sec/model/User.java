package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int uid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date access;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date created;

	@Lob
	private byte[] data;

	@Column(length=254)
	private String init;

	@Column(nullable=false, length=12)
	private String language;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date login;

	@Column(length=254)
	private String mail;

	@Column(nullable=false, length=60)
	private String name;

	@Column(nullable=false, length=128)
	private String pass;

	@Column(nullable=false)
	private int picture;

	@Column(nullable=false, length=255)
	private String signature;

	@Column(name="signature_format", length=255)
	private String signatureFormat;

	@Column(nullable=false)
	private byte status;

	@Column(nullable=false, length=255)
	private String theme;

	@Column(length=32)
	private String timezone;

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="users")
	private List<Role> roles;

	public User() {
	}

	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getAccess() {
		return this.access;
	}

	public void setAccess(Date access) {
		this.access = access;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getInit() {
		return this.init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getLogin() {
		return this.login;
	}

	public void setLogin(Date login) {
		this.login = login;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPicture() {
		return this.picture;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignatureFormat() {
		return this.signatureFormat;
	}

	public void setSignatureFormat(String signatureFormat) {
		this.signatureFormat = signatureFormat;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}