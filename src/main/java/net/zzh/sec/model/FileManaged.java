package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the file_managed database table.
 * 
 */
@Entity
@Table(name="file_managed")
@NamedQuery(name="FileManaged.findAll", query="SELECT f FROM FileManaged f")
public class FileManaged implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int fid;

	@Column(nullable=false, length=255)
	private String filemime;

	@Column(nullable=false, length=255)
	private String filename;

	@Column(nullable=false)
	private int filesize;

	@Column(nullable=false)
	private byte status;

	@Column(nullable=false)
	private int timestamp;

	@Column(nullable=false)
	private int uid;

	@Column(nullable=false, length=255)
	private String uri;

	//bi-directional many-to-one association to FileUsage
	@OneToMany(mappedBy="fileManaged")
	private Set<FileUsage> fileUsages;

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

	public int getFilesize() {
		return this.filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
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

	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Set<FileUsage> getFileUsages() {
		return this.fileUsages;
	}

	public void setFileUsages(Set<FileUsage> fileUsages) {
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