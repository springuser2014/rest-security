package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sessions database table.
 * 
 */
@Entity
@Table(name="sessions")
@NamedQuery(name="Session.findAll", query="SELECT s FROM Session s")
public class Session implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SessionPK id;

	private String hostname;

	private Object session;

	private int timestamp;

	//bi-directional many-to-one association to Users
	@ManyToOne
	@JoinColumn(name="uid")
	private Users user;

	public Session() {
	}

	public SessionPK getId() {
		return this.id;
	}

	public void setId(SessionPK id) {
		this.id = id;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Object getSession() {
		return this.session;
	}

	public void setSession(Object session) {
		this.session = session;
	}

	public int getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}