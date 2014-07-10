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
public class Watchdog implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WatchdogPK id;

	@Column(nullable=false, length=128)
	private String hostname;

	@Column(length=255)
	private String link;

	@Column(nullable=false)
	private Object location;

	@Column(nullable=false)
	private Object message;

	private Object referer;

	@Column(nullable=false)
	private byte severity;

	@Column(nullable=false)
	private int timestamp;

	@Column(nullable=false, length=64)
	private String type;

	@Column(nullable=false)
	private Object variables;

	public Watchdog() {
	}

	public WatchdogPK getId() {
		return this.id;
	}

	public void setId(WatchdogPK id) {
		this.id = id;
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

}