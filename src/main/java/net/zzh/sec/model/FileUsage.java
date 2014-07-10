package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the file_usage database table.
 * 
 */
@Entity
@Table(name="file_usage")
@NamedQuery(name="FileUsage.findAll", query="SELECT f FROM FileUsage f")
public class FileUsage implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FileUsagePK id;

	@Column(nullable=false)
	private int count;

	//bi-directional many-to-one association to FileManaged
	@ManyToOne
	@JoinColumn(name="fid", nullable=false, insertable=false, updatable=false)
	private FileManaged fileManaged;

	public FileUsage() {
	}

	public FileUsagePK getId() {
		return this.id;
	}

	public void setId(FileUsagePK id) {
		this.id = id;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public FileManaged getFileManaged() {
		return this.fileManaged;
	}

	public void setFileManaged(FileManaged fileManaged) {
		this.fileManaged = fileManaged;
	}

}