package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="Users.findAll", query="SELECT u FROM Users u")
public class Users implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int uid;

	private int access;

	private int created;

	private String init;

	private String langcode;

	private int login;

	private String mail;

	private String name;

	private String pass;

	@Column(name="preferred_admin_langcode")
	private String preferredAdminLangcode;

	@Column(name="preferred_langcode")
	private String preferredLangcode;

	private String signature;

	@Column(name="signature_format")
	private String signatureFormat;

	private byte status;

	private String theme;

	private String timezone;

	private String uuid;

	//bi-directional many-to-one association to FileManaged
	@OneToMany(mappedBy="user")
	private List<FileManaged> fileManageds;

	//bi-directional many-to-one association to Session
	@OneToMany(mappedBy="user")
	private List<Session> sessions;

	//bi-directional one-to-one association to ShortcutSetUser
	@OneToOne(mappedBy="user")
	private ShortcutSetUser shortcutSetUser;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="users_roles"
		, joinColumns={
			@JoinColumn(name="uid")
			}
		, inverseJoinColumns={
			@JoinColumn(name="rid")
			}
		)
	private List<Role> roles;

	//bi-directional many-to-one association to UsersData
	@OneToMany(mappedBy="user")
	private List<UsersData> usersData;

	//bi-directional many-to-one association to UsersRole
	@OneToMany(mappedBy="user")
	private List<UsersRole> usersRoles;

	//bi-directional many-to-one association to Watchdog
	@OneToMany(mappedBy="user")
	private List<Watchdog> watchdogs;

	public Users() {
	}

	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getAccess() {
		return this.access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public int getCreated() {
		return this.created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public String getInit() {
		return this.init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public int getLogin() {
		return this.login;
	}

	public void setLogin(int login) {
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

	public String getPreferredAdminLangcode() {
		return this.preferredAdminLangcode;
	}

	public void setPreferredAdminLangcode(String preferredAdminLangcode) {
		this.preferredAdminLangcode = preferredAdminLangcode;
	}

	public String getPreferredLangcode() {
		return this.preferredLangcode;
	}

	public void setPreferredLangcode(String preferredLangcode) {
		this.preferredLangcode = preferredLangcode;
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

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<FileManaged> getFileManageds() {
		return this.fileManageds;
	}

	public void setFileManageds(List<FileManaged> fileManageds) {
		this.fileManageds = fileManageds;
	}

	public FileManaged addFileManaged(FileManaged fileManaged) {
		getFileManageds().add(fileManaged);
		fileManaged.setUser(this);

		return fileManaged;
	}

	public FileManaged removeFileManaged(FileManaged fileManaged) {
		getFileManageds().remove(fileManaged);
		fileManaged.setUser(null);

		return fileManaged;
	}

	public List<Session> getSessions() {
		return this.sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public Session addSession(Session session) {
		getSessions().add(session);
		session.setUser(this);

		return session;
	}

	public Session removeSession(Session session) {
		getSessions().remove(session);
		session.setUser(null);

		return session;
	}

	public ShortcutSetUser getShortcutSetUser() {
		return this.shortcutSetUser;
	}

	public void setShortcutSetUser(ShortcutSetUser shortcutSetUser) {
		this.shortcutSetUser = shortcutSetUser;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<UsersData> getUsersData() {
		return this.usersData;
	}

	public void setUsersData(List<UsersData> usersData) {
		this.usersData = usersData;
	}

	public UsersData addUsersData(UsersData usersData) {
		getUsersData().add(usersData);
		usersData.setUser(this);

		return usersData;
	}

	public UsersData removeUsersData(UsersData usersData) {
		getUsersData().remove(usersData);
		usersData.setUser(null);

		return usersData;
	}

	public List<UsersRole> getUsersRoles() {
		return this.usersRoles;
	}

	public void setUsersRoles(List<UsersRole> usersRoles) {
		this.usersRoles = usersRoles;
	}

	public UsersRole addUsersRole(UsersRole usersRole) {
		getUsersRoles().add(usersRole);
		usersRole.setUser(this);

		return usersRole;
	}

	public UsersRole removeUsersRole(UsersRole usersRole) {
		getUsersRoles().remove(usersRole);
		usersRole.setUser(null);

		return usersRole;
	}

	public List<Watchdog> getWatchdogs() {
		return this.watchdogs;
	}

	public void setWatchdogs(List<Watchdog> watchdogs) {
		this.watchdogs = watchdogs;
	}

	public Watchdog addWatchdog(Watchdog watchdog) {
		getWatchdogs().add(watchdog);
		watchdog.setUser(this);

		return watchdog;
	}

	public Watchdog removeWatchdog(Watchdog watchdog) {
		getWatchdogs().remove(watchdog);
		watchdog.setUser(null);

		return watchdog;
	}

}