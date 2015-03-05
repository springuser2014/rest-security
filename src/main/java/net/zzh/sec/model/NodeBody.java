package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the node_body database table.
 * 
 */
@Entity
@Table(name="node_body")
@NamedQuery(name="NodeBody.findAll", query="SELECT n FROM NodeBody n")
public class NodeBody implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeBodyPK id;

	@Column(name="body_format")
	private String bodyFormat;

	@Column(name="body_summary")
	private Object bodySummary;

	@Column(name="body_value")
	private Object bodyValue;

	private String bundle;

	@Column(name="revision_id")
	private int revisionId;

	//bi-directional many-to-one association to Node
	@ManyToOne
	@JoinColumn(name="entity_id")
	private Node node;

	public NodeBody() {
	}

	public NodeBodyPK getId() {
		return this.id;
	}

	public void setId(NodeBodyPK id) {
		this.id = id;
	}

	public String getBodyFormat() {
		return this.bodyFormat;
	}

	public void setBodyFormat(String bodyFormat) {
		this.bodyFormat = bodyFormat;
	}

	public Object getBodySummary() {
		return this.bodySummary;
	}

	public void setBodySummary(Object bodySummary) {
		this.bodySummary = bodySummary;
	}

	public Object getBodyValue() {
		return this.bodyValue;
	}

	public void setBodyValue(Object bodyValue) {
		this.bodyValue = bodyValue;
	}

	public String getBundle() {
		return this.bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public int getRevisionId() {
		return this.revisionId;
	}

	public void setRevisionId(int revisionId) {
		this.revisionId = revisionId;
	}

	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

}