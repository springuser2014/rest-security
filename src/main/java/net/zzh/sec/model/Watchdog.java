package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the watchdog database table.
 * 
 */
@Entity
@Table(name="watchdog")
@NamedQuery(name="Watchdog.findAll", query="SELECT w FROM Watchdog w")
public class Watchdog implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int wid;

	private String hostname;

	private String link;

	private Object location;

	private Object message;

	private Object referer;

	private byte severity;

	private int timestamp;

	private String type;

	private Object variables;

	//bi-directional many-to-one association to Users
	@ManyToOne
	@JoinColumn(name="uid")
	private Users user;

	public Watchdog() {
	}

	public int getWid() {
		return this.wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Object getLocation() {
		return this.location;
	}

	public void setLocation(Object location) {
		this.location = location;
	}

	public Object getMessage() {
		return this.message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public Object getReferer() {
		return this.referer;
	}

	public void setReferer(Object referer) {
		this.referer = referer;
	}

	public byte getSeverity() {
		return this.severity;
	}

	public void setSeverity(byte severity) {
		this.severity = severity;
	}

	public int getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getVariables() {
		return this.variables;
	}

	public void setVariables(Object variables) {
		this.variables = variables;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}