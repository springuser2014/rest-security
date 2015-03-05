package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the node_comment database table.
 * 
 */
@Entity
@Table(name="node_comment")
@NamedQuery(name="NodeComment.findAll", query="SELECT n FROM NodeComment n")
public class NodeComment implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeCommentPK id;

	private String bundle;

	@Column(name="comment_status")
	private int commentStatus;

	@Column(name="revision_id")
	private int revisionId;

	//bi-directional many-to-one association to Node
	@ManyToOne
	@JoinColumn(name="entity_id")
	private Node node;

	public NodeComment() {
	}

	public NodeCommentPK getId() {
		return this.id;
	}

	public void setId(NodeCommentPK id) {
		this.id = id;
	}

	public String getBundle() {
		return this.bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public int getCommentStatus() {
		return this.commentStatus;
	}

	public void setCommentStatus(int commentStatus) {
		this.commentStatus = commentStatus;
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