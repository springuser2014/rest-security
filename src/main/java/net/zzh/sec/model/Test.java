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
public class Test implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idtest;

	private String testcol1;

	private String testcol2;

	public Test() {
	}

	public int getIdtest() {
		return this.idtest;
	}

	public void setIdtest(int idtest) {
		this.idtest = idtest;
	}

	public String getTestcol1() {
		return this.testcol1;
	}

	public void setTestcol1(String testcol1) {
		this.testcol1 = testcol1;
	}

	public String getTestcol2() {
		return this.testcol2;
	}

	public void setTestcol2(String testcol2) {
		this.testcol2 = testcol2;
	}

}