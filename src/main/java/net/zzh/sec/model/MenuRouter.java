package net.zzh.sec.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the menu_router database table.
 * 
 */
@Entity
@Table(name="menu_router")
@NamedQuery(name="MenuRouter.findAll", query="SELECT m FROM MenuRouter m")
public class MenuRouter implements Serializable, net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MenuRouterPK id;

	@Column(name="access_arguments")
	private Object accessArguments;

	@Column(name="access_callback")
	private String accessCallback;

	private int context;

	@Column(name="delivery_callback")
	private String deliveryCallback;

	private Object description;

	private int fit;

	@Column(name="include_file")
	private Object includeFile;

	@Column(name="load_functions")
	private Object loadFunctions;

	@Column(name="number_parts")
	private short numberParts;

	@Column(name="page_arguments")
	private Object pageArguments;

	@Column(name="page_callback")
	private String pageCallback;

	private String position;

	@Column(name="tab_parent")
	private String tabParent;

	@Column(name="tab_root")
	private String tabRoot;

	@Column(name="theme_arguments")
	private String themeArguments;

	@Column(name="theme_callback")
	private String themeCallback;

	private String title;

	@Column(name="title_arguments")
	private String titleArguments;

	@Column(name="title_callback")
	private String titleCallback;

	@Column(name="to_arg_functions")
	private Object toArgFunctions;

	private int type;

	private int weight;

	//bi-directional many-to-one association to MenuLink
	@ManyToOne
	@JoinColumn(name="menu_links_mlid")
	private MenuLink menuLink;

	public MenuRouter() {
	}

	public MenuRouterPK getId() {
		return this.id;
	}

	public void setId(MenuRouterPK id) {
		this.id = id;
	}

	public Object getAccessArguments() {
		return this.accessArguments;
	}

	public void setAccessArguments(Object accessArguments) {
		this.accessArguments = accessArguments;
	}

	public String getAccessCallback() {
		return this.accessCallback;
	}

	public void setAccessCallback(String accessCallback) {
		this.accessCallback = accessCallback;
	}

	public int getContext() {
		return this.context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public String getDeliveryCallback() {
		return this.deliveryCallback;
	}

	public void setDeliveryCallback(String deliveryCallback) {
		this.deliveryCallback = deliveryCallback;
	}

	public Object getDescription() {
		return this.description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}

	public int getFit() {
		return this.fit;
	}

	public void setFit(int fit) {
		this.fit = fit;
	}

	public Object getIncludeFile() {
		return this.includeFile;
	}

	public void setIncludeFile(Object includeFile) {
		this.includeFile = includeFile;
	}

	public Object getLoadFunctions() {
		return this.loadFunctions;
	}

	public void setLoadFunctions(Object loadFunctions) {
		this.loadFunctions = loadFunctions;
	}

	public short getNumberParts() {
		return this.numberParts;
	}

	public void setNumberParts(short numberParts) {
		this.numberParts = numberParts;
	}

	public Object getPageArguments() {
		return this.pageArguments;
	}

	public void setPageArguments(Object pageArguments) {
		this.pageArguments = pageArguments;
	}

	public String getPageCallback() {
		return this.pageCallback;
	}

	public void setPageCallback(String pageCallback) {
		this.pageCallback = pageCallback;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTabParent() {
		return this.tabParent;
	}

	public void setTabParent(String tabParent) {
		this.tabParent = tabParent;
	}

	public String getTabRoot() {
		return this.tabRoot;
	}

	public void setTabRoot(String tabRoot) {
		this.tabRoot = tabRoot;
	}

	public String getThemeArguments() {
		return this.themeArguments;
	}

	public void setThemeArguments(String themeArguments) {
		this.themeArguments = themeArguments;
	}

	public String getThemeCallback() {
		return this.themeCallback;
	}

	public void setThemeCallback(String themeCallback) {
		this.themeCallback = themeCallback;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleArguments() {
		return this.titleArguments;
	}

	public void setTitleArguments(String titleArguments) {
		this.titleArguments = titleArguments;
	}

	public String getTitleCallback() {
		return this.titleCallback;
	}

	public void setTitleCallback(String titleCallback) {
		this.titleCallback = titleCallback;
	}

	public Object getToArgFunctions() {
		return this.toArgFunctions;
	}

	public void setToArgFunctions(Object toArgFunctions) {
		this.toArgFunctions = toArgFunctions;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public MenuLink getMenuLink() {
		return this.menuLink;
	}

	public void setMenuLink(MenuLink menuLink) {
		this.menuLink = menuLink;
	}

}