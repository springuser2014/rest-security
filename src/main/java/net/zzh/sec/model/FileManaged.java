package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the file_managed database table.
 * 
 */
@Entity
@Table(name="file_managed")
@NamedQuery(name="FileManaged.findAll", query="SELECT f FROM FileManaged f")
public class FileManaged implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int fid;

	private String filemime;

	private String filename;

	private BigInteger filesize;

	private String langcode;

	private byte status;

	private int timestamp;

	private String uri;

	private String uuid;

	//bi-directional many-to-one association to Users
	@ManyToOne
	@JoinColumn(name="uid")
	private Users user;

	//bi-directional many-to-one association to FileUsage
	@OneToMany(mappedBy="fileManaged")
	private List<FileUsage> fileUsages;

	public FileManaged() {
	}

	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getFilemime() {
		return this.filemime;
	}

	public void setFilemime(String filemime) {
		this.filemime = filemime;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public BigInteger getFilesize() {
		return this.filesize;
	}

	public void setFilesize(BigInteger filesize) {
		this.filesize = filesize;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<FileUsage> getFileUsages() {
		return this.fileUsages;
	}

	public void setFileUsages(List<FileUsage> fileUsages) {
		this.fileUsages = fileUsages;
	}

	public FileUsage addFileUsage(FileUsage fileUsage) {
		getFileUsages().add(fileUsage);
		fileUsage.setFileManaged(this);

		return fileUsage;
	}

	public FileUsage removeFileUsage(FileUsage fileUsage) {
		getFileUsages().remove(fileUsage);
		fileUsage.setFileManaged(null);

		return fileUsage;
	}

}