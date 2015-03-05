package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the node database table.
 * 
 */
@Entity
@Table(name="node")
@NamedQuery(name="Node.findAll", query="SELECT n FROM Node n")
public class Node implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int nid;

	private String type;

	private String uuid;

	private int vid;

	//bi-directional many-to-one association to NodeAccess
	@OneToMany(mappedBy="node")
	private List<NodeAccess> nodeAccesses;

	//bi-directional many-to-one association to NodeBody
	@OneToMany(mappedBy="node")
	private List<NodeBody> nodeBodies;

	//bi-directional many-to-one association to NodeComment
	@OneToMany(mappedBy="node")
	private List<NodeComment> nodeComments;

	public Node() {
	}

	public int getNid() {
		return this.nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getVid() {
		return this.vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public List<NodeAccess> getNodeAccesses() {
		return this.nodeAccesses;
	}

	public void setNodeAccesses(List<NodeAccess> nodeAccesses) {
		this.nodeAccesses = nodeAccesses;
	}

	public NodeAccess addNodeAccess(NodeAccess nodeAccess) {
		getNodeAccesses().add(nodeAccess);
		nodeAccess.setNode(this);

		return nodeAccess;
	}

	public NodeAccess removeNodeAccess(NodeAccess nodeAccess) {
		getNodeAccesses().remove(nodeAccess);
		nodeAccess.setNode(null);

		return nodeAccess;
	}

	public List<NodeBody> getNodeBodies() {
		return this.nodeBodies;
	}

	public void setNodeBodies(List<NodeBody> nodeBodies) {
		this.nodeBodies = nodeBodies;
	}

	public NodeBody addNodeBody(NodeBody nodeBody) {
		getNodeBodies().add(nodeBody);
		nodeBody.setNode(this);

		return nodeBody;
	}

	public NodeBody removeNodeBody(NodeBody nodeBody) {
		getNodeBodies().remove(nodeBody);
		nodeBody.setNode(null);

		return nodeBody;
	}

	public List<NodeComment> getNodeComments() {
		return this.nodeComments;
	}

	public void setNodeComments(List<NodeComment> nodeComments) {
		this.nodeComments = nodeComments;
	}

	public NodeComment addNodeComment(NodeComment nodeComment) {
		getNodeComments().add(nodeComment);
		nodeComment.setNode(this);

		return nodeComment;
	}

	public NodeComment removeNodeComment(NodeComment nodeComment) {
		getNodeComments().remove(nodeComment);
		nodeComment.setNode(null);

		return nodeComment;
	}

}