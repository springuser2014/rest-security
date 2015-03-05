package net.zzh.sec.persistence.setup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.zzh.common.event.BeforeSetupEvent;
import net.zzh.common.persistence.service.IPersistenceService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.spring.CommonSpringProfileUtil;
import net.zzh.common.util.SearchField;
import net.zzh.common.web.WebConstants;
import net.zzh.sec.model.Role;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.model.RolePermissionPK;
import net.zzh.sec.model.RolePermission_;
import net.zzh.sec.model.Role_;
import net.zzh.sec.model.Users;
import net.zzh.sec.model.Users_;
import net.zzh.sec.persistence.service.IRolePermissionService;
import net.zzh.sec.persistence.service.IRoleService;
import net.zzh.sec.persistence.service.IUsersService;
import net.zzh.sec.util.SecurityConstants;
import net.zzh.sec.util.SecurityConstants.Modules;
import net.zzh.sec.util.SecurityConstants.Privileges;
import net.zzh.sec.util.SecurityConstants.Roles;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

/**
 * 系统初始化，检查数据库是否存在管理员
 * @author zzh
 *
 */
@Component
@Profile(CommonSpringProfileUtil.DEPLOYED)
public class SecuritySetup implements ApplicationListener<ContextRefreshedEvent> {
	private final Logger logger = LoggerFactory.getLogger(SecuritySetup.class);

	private boolean setupDone;

	@Autowired
	//private IUserService principalService;
	private IUsersService usersService;

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IRolePermissionService rolePermissionService;
	
	@Autowired
	private ApplicationContext eventPublisher;

	@Autowired
	private IPersistenceService persistenceService;
	
	@Autowired(required=false)
	@PersistenceContext
	private EntityManager em;
	
	public SecuritySetup() {
		super();
	}

	//

	/**
	 * - note that this is a compromise - the flag makes this bean statefull which can (and will) be avoided in the future by a more advanced mechanism <br>
	 * - the reason for this is that the context is refreshed more than once throughout the lifecycle of the deployable <br>
	 * - alternatives: proper persisted versioning
	 */
	public final void onApplicationEvent(final ContextRefreshedEvent event) {
		if (!setupDone) {
			logger.debug("Executing Setup");
			eventPublisher.publishEvent(new BeforeSetupEvent(this));

			/*
			 * privilegeService.deleteAll(); roleService.deleteAll(); principalService.deleteAll();
			 */
			createRoles();
			createUsers();
			createPrivileges();
			
			//System.out.println(logger.isDebugEnabled());
			//procService.delete(0);
			
			//List list = privilegeService.searchAll("SELECT priv_id,description,name FROM rest.privilege");
			//System.out.println(list.size());
			//Page page = persistenceService.findPaginated(1, 10, "select name,(select ROLE_ID from Role_Privilege where a.PRIV_ID = PRIV_ID) from Privilege a");
			//Page page = persistenceService.findPaginated(1, 10, "select a.username,(select b.s0204 from S02 b where b.s0200 in (select c.s0301 from S03 c where c.s0302 = a.id)) from Jiveuser a");

			/*
			 *  success
			 */

//			CriteriaBuilder builder = em.getCriteriaBuilder();
//			CriteriaQuery<Test> query = builder.createQuery(Test.class);
//			Root<Test> root = query.from(Test.class);
//
//			Predicate hasBirthday = builder.equal(root.get(Test_.idtest), 1);
//			//Predicate isLongTermTest = builder.lessThan(root.get(Test_.createdAt), today.minusYears(2); 
//			query.where(builder.and(hasBirthday));
//			List list = em.createQuery(query.select(root)).getResultList();
//			System.out.println(list.size());
			
			// Create
			//Test test = new Test();
			//test.setTestName((new Date()).toString());
			//testService.create(test);
			
			// Update
			//Test test = testService.findOne(1);
			//test.setTestName((new Date()).toString());
			//testService.update(test);
			
			//Proc proc = procService.findOne(1);
			//System.out.println(proc.getName());
			//List list = procService.findAllPaginated(1, 6);
			//Page page = persistenceService.findPaginated(1, 10, "SELECT id,name FROM Proc");
			//Page page = persistenceService.findNativeQueryPaginated(1, 10, "SELECT proc_id, proc_name FROM proc");
			
			// Find
			//List list = persistenceService.findByNativeSQL("select principal_id, name, password, locked from principal");
			//List list = privilegeService.findAll();
			//Privilege Privilege = privilegeService.findOne(1);
			//Privilege = privilegeService.findByName("ROLE_PRIVILEGE_READ");
			//Page page = persistenceService.findPaginated(1, 10, "select id, name from Privilege a");
			//System.out.println(Privilege.getName());
			
			// JPA
			//Page page = persistenceService.findPaginated(1, 10, "select a, (select c.id from Jiveuser c where c.id = a.s02.id) from S03 a");
			//Page page = persistenceService.findPaginated(1, 10, "select a from S03 a where a.s02.id in (select c.id from S02 c)");
			//Page page = persistenceService.findPaginated(1, 10, "select a, b.id from S03 a, in(a.s02) b where b.s0200 = 1");
			
			// Native query
			//Page page = persistenceService.findNativeQueryPaginated(1, 10, "select s0300,s0301,s0302 from S03 a");

			//System.out.println(list.size());
			//System.out.println(page.getTotalPages());
			setupDone = true;
			logger.debug("Setup Done");
		}
	}

	/**
	 * Privilege
	 * 创建权限
	 */
	private void createPrivileges() {
		/*
		createPrivilegeIfNotExisting(Privileges.CAN_PRIVILEGE_READ);
		createPrivilegeIfNotExisting(Privileges.CAN_PRIVILEGE_WRITE);

		createPrivilegeIfNotExisting(Privileges.CAN_ROLE_READ);
		createPrivilegeIfNotExisting(Privileges.CAN_ROLE_WRITE);

		createPrivilegeIfNotExisting(Privileges.CAN_USER_READ);
		createPrivilegeIfNotExisting(Privileges.CAN_USER_WRITE);
		*/

		int rid = 0;
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Role> query = builder.createQuery(Role.class);
		Root<Role> root = query.from(Role.class);

		Predicate hasName = builder.equal(root.get(Role_.name), Roles.ADMINISTRATOR);
		query.where(builder.and(hasName));
		
		List list = em.createQuery(query.select(root)).getResultList();
		if(!list.isEmpty()) {
			Role role = (Role) list.get(0);
			rid = role.getRid();
		}
		
		// User module
		createPrivilegeIfNotExisting(rid, Privileges.ACCESS_USER_PROFILES, Modules.USER);
		createPrivilegeIfNotExisting(rid, Privileges.ADMINISTER_PERMISSIONS, Modules.USER);
		createPrivilegeIfNotExisting(rid, Privileges.ADMINISTER_USERS, Modules.USER);
		createPrivilegeIfNotExisting(rid, Privileges.CHANGE_OWN_USERNAME, Modules.USER);
		
		// System module
		createPrivilegeIfNotExisting(rid, Privileges.ACCESS_ADMINISTRATION_PAGES, Modules.SYSTEM);
		createPrivilegeIfNotExisting(rid, Privileges.ACCESS_SITE_IN_MAINTENANCE_MODE, Modules.SYSTEM);
		createPrivilegeIfNotExisting(rid, Privileges.ACCESS_SITE_REPORTS, Modules.SYSTEM);
		createPrivilegeIfNotExisting(rid, Privileges.ADMINISTER_MODULES, Modules.SYSTEM);
		createPrivilegeIfNotExisting(rid, Privileges.ADMINISTER_SITE_CONFIGURATION, Modules.SYSTEM);
		createPrivilegeIfNotExisting(rid, Privileges.ADMINISTER_THEMES, Modules.SYSTEM);
		createPrivilegeIfNotExisting(rid, Privileges.BLOCK_IP_ADDRESSES, Modules.SYSTEM);
	}

	/**
	 * 如果不存在,创建权限
	 * @param name
	 */
	final void createPrivilegeIfNotExisting(final int roleID, final String name, final String module) {
		/*final Privilege entityByName = privilegeService.findByName(name);
		if (entityByName == null) {
			final Privilege entity = new Privilege(name);
			privilegeService.create(entity);
		}*/
		Role role = roleService.findOne(roleID);
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<RolePermission> query = builder.createQuery(RolePermission.class);
		Root<RolePermission> root = query.from(RolePermission.class);

		query.where(builder.and(
				builder.equal(root.get(RolePermission_.role), role),
				builder.equal(root.get(RolePermission_.module), module)
				));
		
		List list = em.createQuery(query.select(root)).getResultList();
		if(list.isEmpty()) {
			RolePermission rolePermission = new RolePermission();
			RolePermissionPK pk = new RolePermissionPK ();
			pk.setRid(roleID);
			pk.setPermission(name);
			rolePermission.setId(pk);
			rolePermission.setModule(module);
			rolePermissionService.create(rolePermission);
		}
	}

	/**
	 * Role
	 * 创建角色
	 */
	private void createRoles() {
/*		final Privilege canPrivilegeRead = privilegeService.findByName(Privileges.CAN_PRIVILEGE_READ);
		final Privilege canPrivilegeWrite = privilegeService.findByName(Privileges.CAN_PRIVILEGE_WRITE);
		final Privilege canRoleRead = privilegeService.findByName(Privileges.CAN_ROLE_READ);
		final Privilege canRoleWrite = privilegeService.findByName(Privileges.CAN_ROLE_WRITE);
		final Privilege canUserRead = privilegeService.findByName(Privileges.CAN_USER_READ);
		final Privilege canUserWrite = privilegeService.findByName(Privileges.CAN_USER_WRITE);

		Preconditions.checkNotNull(canPrivilegeRead);
		Preconditions.checkNotNull(canPrivilegeWrite);
		Preconditions.checkNotNull(canRoleRead);
		Preconditions.checkNotNull(canRoleWrite);
		Preconditions.checkNotNull(canUserRead);
		Preconditions.checkNotNull(canUserWrite);

		createRoleIfNotExisting(Roles.ROLE_ADMIN, Sets.<Privilege> newHashSet(canUserRead, canUserWrite, canRoleRead, canRoleWrite, canPrivilegeRead, canPrivilegeWrite));
		*/
		createRoleIfNotExisting(Roles.ADMINISTRATOR);
		createRoleIfNotExisting(Roles.AUTHENTICATED_USER);
		
	}

	/**
	 * 如果不存在,创建角色
	 * @param name
	 */
	final void createRoleIfNotExisting(final String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Role> query = builder.createQuery(Role.class);
		Root<Role> root = query.from(Role.class);

		Predicate hasName = builder.equal(root.get(Role_.name), name);
		query.where(builder.and(hasName));
		
		List list = em.createQuery(query.select(root)).getResultList();
		if(list.isEmpty()) {
			final Role role = new Role();
			role.setName(name);
			role.setWeight(0);
			roleService.create(role);
		}
	}

	/**
	 * User
	 * 创建用户
	 */
	final void createUsers() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Role> query = builder.createQuery(Role.class);
		Root<Role> root = query.from(Role.class);

		Predicate hasName = builder.equal(root.get(Role_.name), Roles.ADMINISTRATOR);
		query.where(builder.and(hasName));
		
		List list = em.createQuery(query.select(root)).getResultList();
		final Role roleAdmin = (Role) list.get(0);
		
		createUserIfNotExisting(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASS, Sets.<Role> newHashSet(roleAdmin));
	}

	/**
	 * 如果不存在创建管理员
	 * @param loginName
	 * @param pass
	 * @param roles
	 */
	final void createUserIfNotExisting(final String loginName, final String pass, final Set<Role> roles) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Users> query = builder.createQuery(Users.class);
		Root<Users> root = query.from(Users.class);

		Predicate hasName = builder.equal(root.get(Users_.name), loginName);
		query.where(builder.and(hasName));
		
		List list = em.createQuery(query.select(root)).getResultList();
		if (list.isEmpty()) {
			final Users user = new Users();
			user.setName(loginName);
			user.setPass(pass);
			List<Role> roleList = new ArrayList<Role>();
			roleList.addAll(roles);
			user.setRoles(roleList);
			user.setTheme(WebConstants.ORIGINAL_DEFAULT_THEME_NAME);
			user.setSignature("User’s signature.");
			Long created = new Date().getTime();
			user.setCreated(created.intValue());
			user.setAccess(created.intValue());
			user.setLogin(created.intValue());
			user.setStatus(Byte.parseByte("1"));
			user.setLangcode(WebConstants.DEFAULT_LANGUAGE);
			usersService.create(user);
		}
	}

}
