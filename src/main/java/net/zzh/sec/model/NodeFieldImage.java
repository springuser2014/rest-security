package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the node_field_image database table.
 * 
 */
@Entity
@Table(name="node_field_image")
@NamedQuery(name="NodeFieldImage.findAll", query="SELECT n FROM NodeFieldImage n")
public class NodeFieldImage implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeFieldImagePK id;

	private String bundle;

	@Column(name="field_image_alt")
	private String fieldImageAlt;

	@Column(name="field_image_height")
	private int fieldImageHeight;

	@Column(name="field_image_target_id")
	private int fieldImageTargetId;

	@Column(name="field_image_title")
	private String fieldImageTitle;

	@Column(name="field_image_width")
	private int fieldImageWidth;

	@Column(name="revision_id")
	private int revisionId;

	public NodeFieldImage() {
	}

	public NodeFieldImagePK getId() {
		return this.id;
	}

	public void setId(NodeFieldImagePK id) {
		this.id = id;
	}

	public String getBundle() {
		return this.bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public String getFieldImageAlt() {
		return this.fieldImageAlt;
	}

	public void setFieldImageAlt(String fieldImageAlt) {
		this.fieldImageAlt = fieldImageAlt;
	}

	public int getFieldImageHeight() {
		return this.fieldImageHeight;
	}

	public void setFieldImageHeight(int fieldImageHeight) {
		this.fieldImageHeight = fieldImageHeight;
	}

	public int getFieldImageTargetId() {
		return this.fieldImageTargetId;
	}

	public void setFieldImageTargetId(int fieldImageTargetId) {
		this.fieldImageTargetId = fieldImageTargetId;
	}

	public String getFieldImageTitle() {
		return this.fieldImageTitle;
	}

	public void setFieldImageTitle(String fieldImageTitle) {
		this.fieldImageTitle = fieldImageTitle;
	}

	public int getFieldImageWidth() {
		return this.fieldImageWidth;
	}

	public void setFieldImageWidth(int fieldImageWidth) {
		this.fieldImageWidth = fieldImageWidth;
	}

	public int getRevisionId() {
		return this.revisionId;
	}

	public void setRevisionId(int revisionId) {
		this.revisionId = revisionId;
	}

}