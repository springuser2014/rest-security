package net.zzh.sec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * The persistent class for the proc database table.
 * 
 */
@Entity
@Table(name="proc")
@NamedQuery(name="Proc.findAll", query="SELECT p FROM Proc p")
@XmlRootElement
@XStreamAlias("proc")
public class Proc implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "proc_id")
    private Long id;

	@Column(name="proc_name", nullable=false, length=45)
	private String name;

	public Proc() {
        super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String procName) {
		this.name = procName;
	}

}