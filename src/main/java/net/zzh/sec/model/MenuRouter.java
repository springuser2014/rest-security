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
public class MenuRouter implements net.zzh.common.persistence.model.INameableEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MenuRouterPK id;

	@Lob
	@Column(name="access_arguments")
	private byte[] accessArguments;

	@Column(name="access_callback", nullable=false, length=255)
	private String accessCallback;

	@Column(nullable=false)
	private int context;

	@Column(name="delivery_callback", nullable=false, length=255)
	private String deliveryCallback;

	@Lob
	@Column(nullable=false)
	private String description;

	@Column(nullable=false)
	private int fit;

	@Lob
	@Column(name="include_file")
	private String includeFile;

	@Lob
	@Column(name="load_functions", nullable=false)
	private byte[] loadFunctions;

	@Column(name="number_parts", nullable=false)
	private short numberParts;

	@Lob
	@Column(name="page_arguments")
	private byte[] pageArguments;

	@Column(name="page_callback", nullable=false, length=255)
	private String pageCallback;

	@Column(nullable=false, length=255)
	private String position;

	@Column(name="tab_parent", nullable=false, length=255)
	private String tabParent;

	@Column(name="tab_root", nullable=false, length=255)
	private String tabRoot;

	@Column(name="theme_arguments", nullable=false, length=255)
	private String themeArguments;

	@Column(name="theme_callback", nullable=false, length=255)
	private String themeCallback;

	@Column(nullable=false, length=255)
	private String title;

	@Column(name="title_arguments", nullable=false, length=255)
	private String titleArguments;

	@Column(name="title_callback", nullable=false, length=255)
	private String titleCallback;

	@Lob
	@Column(name="to_arg_functions", nullable=false)
	private byte[] toArgFunctions;

	@Column(nullable=false)
	private int type;

	@Column(nullable=false)
	private int weight;

	//bi-directional many-to-one association to MenuLink
	@ManyToOne
	@JoinColumn(name="menu_links_mlid", nullable=false, insertable=false, updatable=false)
	private MenuLink menuLink;

	public MenuRouter() {
	}

	public MenuRouterPK getId() {
		return this.id;
	}

	public void setId(MenuRouterPK id) {
		this.id = id;
	}

	public byte[] getAccessArguments() {
		return this.accessArguments;
	}

	public void setAccessArguments(byte[] accessArguments) {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFit() {
		return this.fit;
	}

	public void setFit(int fit) {
		this.fit = fit;
	}

	public String getIncludeFile() {
		return this.includeFile;
	}

	public void setIncludeFile(String includeFile) {
		this.includeFile = includeFile;
	}

	public byte[] getLoadFunctions() {
		return this.loadFunctions;
	}

	public void setLoadFunctions(byte[] loadFunctions) {
		this.loadFunctions = loadFunctions;
	}

	public short getNumberParts() {
		return this.numberParts;
	}

	public void setNumberParts(short numberParts) {
		this.numberParts = numberParts;
	}

	public byte[] getPageArguments() {
		return this.pageArguments;
	}

	public void setPageArguments(byte[] pageArguments) {
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

	public byte[] getToArgFunctions() {
		return this.toArgFunctions;
	}

	public void setToArgFunctions(byte[] toArgFunctions) {
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