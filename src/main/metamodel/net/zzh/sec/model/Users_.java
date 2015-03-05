package net.zzh.sec.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> init;
	public static volatile ListAttribute<Users, Session> sessions;
	public static volatile SingularAttribute<Users, Integer> access;
	public static volatile SingularAttribute<Users, String> mail;
	public static volatile SingularAttribute<Users, String> signatureFormat;
	public static volatile SingularAttribute<Users, String> pass;
	public static volatile SingularAttribute<Users, String> preferredAdminLangcode;
	public static volatile SingularAttribute<Users, String> signature;
	public static volatile SingularAttribute<Users, Integer> created;
	public static volatile SingularAttribute<Users, String> timezone;
	public static volatile ListAttribute<Users, Role> roles;
	public static volatile ListAttribute<Users, Watchdog> watchdogs;
	public static volatile SingularAttribute<Users, Integer> login;
	public static volatile SingularAttribute<Users, String> uuid;
	public static volatile SingularAttribute<Users, String> preferredLangcode;
	public static volatile SingularAttribute<Users, String> langcode;
	public static volatile SingularAttribute<Users, ShortcutSetUser> shortcutSetUser;
	public static volatile SingularAttribute<Users, Integer> uid;
	public static volatile ListAttribute<Users, UsersData> usersData;
	public static volatile ListAttribute<Users, FileManaged> fileManageds;
	public static volatile ListAttribute<Users, UsersRole> usersRoles;
	public static volatile SingularAttribute<Users, String> name;
	public static volatile SingularAttribute<Users, String> theme;
	public static volatile SingularAttribute<Users, Byte> status;

}

