package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the node_field_tags database table.
 * 
 */
@Entity
@Table(name="node_field_tags")
@NamedQuery(name="NodeFieldTag.findAll", query="SELECT n FROM NodeFieldTag n")
public class NodeFieldTag implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeFieldTagPK id;

	private String bundle;

	@Column(name="field_tags_target_id")
	private int fieldTagsTargetId;

	@Column(name="revision_id")
	private int revisionId;

	public NodeFieldTag() {
	}

	public NodeFieldTagPK getId() {
		return this.id;
	}

	public void setId(NodeFieldTagPK id) {
		this.id = id;
	}

	public String getBundle() {
		return this.bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public int getFieldTagsTargetId() {
		return this.fieldTagsTargetId;
	}

	public void setFieldTagsTargetId(int fieldTagsTargetId) {
		this.fieldTagsTargetId = fieldTagsTargetId;
	}

	public int getRevisionId() {
		return this.revisionId;
	}

	public void setRevisionId(int revisionId) {
		this.revisionId = revisionId;
	}

}