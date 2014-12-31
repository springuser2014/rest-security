package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the test database table.
 * 
 */
@Entity
@Table(name="test")
@NamedQuery(name="Test.findAll", query="SELECT t FROM Test t")
public class Test implements net.zzh.common.persistence.model.INameableEntity, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idtest;

	@Column(length=45)
	private String testcol;

	public Test() {
	}

	public int getIdtest() {
		return this.idtest;
	}

	public void setIdtest(int idtest) {
		this.idtest = idtest;
	}

	public String getTestcol() {
		return this.testcol;
	}

	public void setTestcol(String testcol) {
		this.testcol = testcol;
	}

}