package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the menu_router database table.
 * 
 */
@Embeddable
public class MenuRouterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(unique=true, nullable=false, length=255)
	private String path;

	@Column(name="menu_links_mlid", insertable=false, updatable=false, unique=true, nullable=false)
	private int menuLinksMlid;

	public MenuRouterPK() {
	}
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
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
		if (!(other instanceof MenuRouterPK)) {
			return false;
		}
		MenuRouterPK castOther = (MenuRouterPK)other;
		return 
			this.path.equals(castOther.path)
			&& (this.menuLinksMlid == castOther.menuLinksMlid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.path.hashCode();
		hash = hash * prime + this.menuLinksMlid;
		
		return hash;
	}
}