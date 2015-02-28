package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the system database table.
 * 
 */
@Entity
@Table(name="system")
@NamedQuery(name="System.findAll", query="SELECT s FROM System s")
public class System implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String filename;

	private int bootstrap;

	private Object info;

	private String name;

	private String owner;

	@Column(name="schema_version")
	private short schemaVersion;

	private int status;

	private String type;

	private int weight;

	public System() {
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getBootstrap() {
		return this.bootstrap;
	}

	public void setBootstrap(int bootstrap) {
		this.bootstrap = bootstrap;
	}

	public Object getInfo() {
		return this.info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public short getSchemaVersion() {
		return this.schemaVersion;
	}

	public void setSchemaVersion(short schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}