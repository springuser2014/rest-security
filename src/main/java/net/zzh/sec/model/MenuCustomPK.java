package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the menu_custom database table.
 * 
 */
@Embeddable
public class MenuCustomPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="menu_name")
	private String menuName;

	@Column(name="menu_links_mlid", insertable=false, updatable=false)
	private int menuLinksMlid;

	public MenuCustomPK() {
	}
	public String getMenuName() {
		return this.menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getMenuLinksMlid() {
		return this.menuLinksMlid;
	}
	public void setMenuLinksMlid(int menuLinksMlid) {
		this.menuLinksMlid = menuLinksMlid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MenuCustomPK)) {
			return false;
		}
		MenuCustomPK castOther = (MenuCustomPK)other;
		return 
			this.menuName.equals(castOther.menuName)
			&& (this.menuLinksMlid == castOther.menuLinksMlid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.menuName.hashCode();
		hash = hash * prime + this.menuLinksMlid;
		
		return hash;
	}
}