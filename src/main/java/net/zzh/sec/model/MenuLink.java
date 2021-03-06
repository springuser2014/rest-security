package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the menu_links database table.
 * 
 */
@Entity
@Table(name="menu_links")
@NamedQuery(name="MenuLink.findAll", query="SELECT m FROM MenuLink m")
public class MenuLink implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int mlid;

	private short customized;

	private short depth;

	private short expanded;

	private short external;

	@Column(name="has_children")
	private short hasChildren;

	private short hidden;

	private String langcode;

	@Column(name="link_path")
	private String linkPath;

	@Column(name="link_title")
	private String linkTitle;

	@Column(name="menu_name")
	private String menuName;

	private String module;

	private Object options;

	private int p1;

	private int p2;

	private int p3;

	private int p4;

	private int p5;

	private int p6;

	private int p7;

	private int p8;

	private int p9;

	private int plid;

	@Column(name="route_name")
	private String routeName;

	@Column(name="route_parameters")
	private Object routeParameters;

	private short updated;

	private String uuid;

	private int weight;

	//bi-directional many-to-one association to MenuRouter
	@ManyToOne
	@JoinColumn(name="router_path")
	private MenuRouter menuRouter;

	public MenuLink() {
	}

	public int getMlid() {
		return this.mlid;
	}

	public void setMlid(int mlid) {
		this.mlid = mlid;
	}

	public short getCustomized() {
		return this.customized;
	}

	public void setCustomized(short customized) {
		this.customized = customized;
	}

	public short getDepth() {
		return this.depth;
	}

	public void setDepth(short depth) {
		this.depth = depth;
	}

	public short getExpanded() {
		return this.expanded;
	}

	public void setExpanded(short expanded) {
		this.expanded = expanded;
	}

	public short getExternal() {
		return this.external;
	}

	public void setExternal(short external) {
		this.external = external;
	}

	public short getHasChildren() {
		return this.hasChildren;
	}

	public void setHasChildren(short hasChildren) {
		this.hasChildren = hasChildren;
	}

	public short getHidden() {
		return this.hidden;
	}

	public void setHidden(short hidden) {
		this.hidden = hidden;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getLinkPath() {
		return this.linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	public String getLinkTitle() {
		return this.linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Object getOptions() {
		return this.options;
	}

	public void setOptions(Object options) {
		this.options = options;
	}

	public int getP1() {
		return this.p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public int getP2() {
		return this.p2;
	}

	public void setP2(int p2) {
		this.p2 = p2;
	}

	public int getP3() {
		return this.p3;
	}

	public void setP3(int p3) {
		this.p3 = p3;
	}

	public int getP4() {
		return this.p4;
	}

	public void setP4(int p4) {
		this.p4 = p4;
	}

	public int getP5() {
		return this.p5;
	}

	public void setP5(int p5) {
		this.p5 = p5;
	}

	public int getP6() {
		return this.p6;
	}

	public void setP6(int p6) {
		this.p6 = p6;
	}

	public int getP7() {
		return this.p7;
	}

	public void setP7(int p7) {
		this.p7 = p7;
	}

	public int getP8() {
		return this.p8;
	}

	public void setP8(int p8) {
		this.p8 = p8;
	}

	public int getP9() {
		return this.p9;
	}

	public void setP9(int p9) {
		this.p9 = p9;
	}

	public int getPlid() {
		return this.plid;
	}

	public void setPlid(int plid) {
		this.plid = plid;
	}

	public String getRouteName() {
		return this.routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Object getRouteParameters() {
		return this.routeParameters;
	}

	public void setRouteParameters(Object routeParameters) {
		this.routeParameters = routeParameters;
	}

	public short getUpdated() {
		return this.updated;
	}

	public void setUpdated(short updated) {
		this.updated = updated;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public MenuRouter getMenuRouter() {
		return this.menuRouter;
	}

	public void setMenuRouter(MenuRouter menuRouter) {
		this.menuRouter = menuRouter;
	}

}