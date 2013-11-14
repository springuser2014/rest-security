package net.zzh.sec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import net.zzh.common.persistence.model.INameableEntity;


/**
 * The persistent class for the test database table.
 * 
 */
@Entity
@Table(name="test")
@NamedQuery(name="Test.findAll", query="SELECT t FROM Test t")
public class Test implements INameableEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="test_id", unique=true, nullable=false, precision=10)
	private Long id;

	@Column(name="test_name", nullable=false, length=45)
	private String testName;

	public Test() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}