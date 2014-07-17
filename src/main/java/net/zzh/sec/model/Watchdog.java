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

	@Lob
	@Column(nullable=false)
	private String location;

	@Lob
	@Column(nullable=false)
	private String message;

	@Lob
	private String referer;

	@Column(nullable=false)
	private byte severity;

	@Column(nullable=false)
	private int timestamp;

	@Column(nullable=false, length=64)
	private String type;

	@Lob
	@Column(nullable=false)
	private byte[] variables;

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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReferer() {
		return this.referer;
	}

	public void setReferer(String referer) {
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

	public byte[] getVariables() {
		return this.variables;
	}

	public void setVariables(byte[] variables) {
		this.variables = variables;
	}

}