package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the menu_custom database table.
 * 
 */
@Entity
@Table(name="menu_custom")
@NamedQuery(name="MenuCustom.findAll", query="SELECT m FROM MenuCustom m")
public class MenuCustom implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MenuCustomPK id;

	private Object description;

	@Column(nullable=false, length=255)
	private String title;

	//bi-directional many-to-one association to MenuLink
	@ManyToOne
	@JoinColumn(name="menu_links_mlid", nullable=false, insertable=false, updatable=false)
	private MenuLink menuLink;

	public MenuCustom() {
	}

	public MenuCustomPK getId() {
		return this.id;
	}

	public void setId(MenuCustomPK id) {
		this.id = id;
	}

	public Object getDescription() {
		return this.description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MenuLink getMenuLink() {
		return this.menuLink;
	}

	public void setMenuLink(MenuLink menuLink) {
		this.menuLink = menuLink;
	}

}