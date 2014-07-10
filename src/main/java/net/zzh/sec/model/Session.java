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
public class Session implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SessionPK id;

	@Column(nullable=false)
	private int cache;

	@Column(nullable=false, length=128)
	private String hostname;

	private Object session;

	@Column(nullable=false)
	private int timestamp;

	@Column(nullable=false)
	private int uid;

	public Session() {
	}

	public SessionPK getId() {
		return this.id;
	}

	public void setId(SessionPK id) {
		this.id = id;
	}

	public int getCache() {
		return this.cache;
	}

	public void setCache(int cache) {
		this.cache = cache;
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

	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

}