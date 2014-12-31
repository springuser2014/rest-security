package net.zzh.sec.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Users.class)
public abstract class Users_ {

	public static volatile SingularAttribute<Users, String> init;
	public static volatile SingularAttribute<Users, Date> access;
	public static volatile SingularAttribute<Users, byte[]> data;
	public static volatile SingularAttribute<Users, String> mail;
	public static volatile SingularAttribute<Users, String> signatureFormat;
	public static volatile SingularAttribute<Users, String> pass;
	public static volatile SingularAttribute<Users, String> signature;
	public static volatile SingularAttribute<Users, Date> created;
	public static volatile SingularAttribute<Users, String> timezone;
	public static volatile ListAttribute<Users, Role> roles;
	public static volatile SingularAttribute<Users, String> language;
	public static volatile SingularAttribute<Users, Date> login;
	public static volatile SingularAttribute<Users, Integer> picture;
	public static volatile SingularAttribute<Users, Integer> uid;
	public static volatile ListAttribute<Users, UsersRole> usersRoles;
	public static volatile SingularAttribute<Users, String> name;
	public static volatile SingularAttribute<Users, String> theme;
	public static volatile SingularAttribute<Users, Byte> status;

}

