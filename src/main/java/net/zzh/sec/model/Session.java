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

	private int cache;

	private String hostname;

	private Object session;

	private int timestamp;

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