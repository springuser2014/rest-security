package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the node_access database table.
 * 
 */
@Entity
@Table(name="node_access")
@NamedQuery(name="NodeAccess.findAll", query="SELECT n FROM NodeAccess n")
public class NodeAccess implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeAccessPK id;

	private int fallback;

	@Column(name="grant_delete")
	private byte grantDelete;

	@Column(name="grant_update")
	private byte grantUpdate;

	@Column(name="grant_view")
	private byte grantView;

	//bi-directional many-to-one association to Node
	@ManyToOne
	@JoinColumn(name="entity_id")
	private Node node;

	public NodeAccess() {
	}

	public NodeAccessPK getId() {
		return this.id;
	}

	public void setId(NodeAccessPK id) {
		this.id = id;
	}

	public int getFallback() {
		return this.fallback;
	}

	public void setFallback(int fallback) {
		this.fallback = fallback;
	}

	public byte getGrantDelete() {
		return this.grantDelete;
	}

	public void setGrantDelete(byte grantDelete) {
		this.grantDelete = grantDelete;
	}

	public byte getGrantUpdate() {
		return this.grantUpdate;
	}

	public void setGrantUpdate(byte grantUpdate) {
		this.grantUpdate = grantUpdate;
	}

	public byte getGrantView() {
		return this.grantView;
	}

	public void setGrantView(byte grantView) {
		this.grantView = grantView;
	}

	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

}