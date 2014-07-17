package net.zzh.sec.persistence.setup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.zzh.common.event.BeforeSetupEvent;
import net.zzh.common.persistence.service.IPersistenceService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.spring.CommonSpringProfileUtil;
import net.zzh.common.util.SearchField;
import net.zzh.common.web.WebConstants;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.model.Role;
import net.zzh.sec.model.RolePermissionPK;
import net.zzh.sec.model.User;
import net.zzh.sec.persistence.service.IRolePermissionService;
import net.zzh.sec.persistence.service.IRoleService;
import net.zzh.sec.persistence.service.IUserService;
import net.zzh.sec.util.SecurityConstants;
import net.zzh.sec.util.SecurityConstants.Modules;
import net.zzh.sec.util.SecurityConstants.Roles;
import net.zzh.sec.util.SecurityConstants.SystemPermissions;
import net.zzh.sec.util.SecurityConstants.UserPermissions;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IRolePermissionService rolePermissionervice;
	
	@Autowired
	private ApplicationContext eventPublisher;

	@Autowired
	private IPersistenceService persistenceService;
	
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
			 * RolePermissionervice.deleteAll(); roleService.deleteAll(); UserService.deleteAll();
			 */
			createRoles();
			createUsers();
			createRolePermission();
			
			//System.out.println(logger.isDebugEnabled());
			//procService.delete(0);
			
			
			//List list = RolePermissionervice.searchAll("SELECT priv_id,description,name FROM rest.RolePermission");
			//System.out.println(list.size());
			//Page page = persistenceService.findPaginated(1, 10, "select name,(select ROLE_ID from Role_RolePermission where a.PRIV_ID = PRIV_ID) from RolePermission a");
			//Page page = persistenceService.findPaginated(1, 10, "select a.username,(select b.s0204 from S02 b where b.s0200 in (select c.s0301 from S03 c where c.s0302 = a.id)) from Jiveuser a");

			/*
			 *  success
			 */
			
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
			//List list = persistenceService.findByNativeSQL("select User_id, name, password, locked from User");
			//List list = RolePermissionervice.findAll();
			//RolePermission RolePermission = RolePermissionervice.findOne(1);
			//RolePermission = RolePermissionervice.findByName("ROLE_RolePermission_READ");
			//Page page = persistenceService.findPaginated(1, 10, "select id, name from RolePermission a");
			//System.out.println(RolePermission.getName());
			
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
	 * RolePermission
	 * 创建权限
	 */
	private void createRolePermission() {
		ImmutableTriple<String, ClientOperation, String> roleNameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, Roles.ADMINISTRATOR);
		final Role role = roleService.searchOne(roleNameConstraint);
		Integer rid = role.getRid();
		
		createRolePermissionIfNotExisting(UserPermissions.ADMINISTER_PERMISSIONS, rid, Modules.USER);
		createRolePermissionIfNotExisting(UserPermissions.ADMINISTER_USERS, rid, Modules.USER);
		createRolePermissionIfNotExisting(UserPermissions.ACCESS_USER_PROFILES, rid, Modules.USER);
		createRolePermissionIfNotExisting(UserPermissions.CHANGE_OWN_USERNAME, rid, Modules.USER);

		createRolePermissionIfNotExisting(SystemPermissions.ACCESS_ADMINISTRATION_PAGES, rid, Modules.SYSTEM);
		createRolePermissionIfNotExisting(SystemPermissions.BLOCK_IP_ADDRESSES, rid, Modules.SYSTEM);
		createRolePermissionIfNotExisting(SystemPermissions.ADMINISTER_MODULES, rid, Modules.SYSTEM);
		createRolePermissionIfNotExisting(SystemPermissions.ADMINISTER_SITE_CONFIGURATION, rid, Modules.SYSTEM);
		createRolePermissionIfNotExisting(SystemPermissions.ADMINISTER_THEMES, rid, Modules.SYSTEM);
		createRolePermissionIfNotExisting(SystemPermissions.VIEW_THE_ADMINISTRATION_THEME, rid, Modules.SYSTEM);
		createRolePermissionIfNotExisting(SystemPermissions.ACCESS_SITE_REPORTS, rid, Modules.SYSTEM);
		createRolePermissionIfNotExisting(SystemPermissions.ACCESS_SITE_IN_MAINTENANCE_MODE, rid, Modules.SYSTEM);
	}

	/**
	 * 如果不存在,创建权限
	 * @param name Permission name
	 * @param rid Role id
	 */
	final void createRolePermissionIfNotExisting(final String name, final Integer rid, final String module) {
		ImmutableTriple<String, ClientOperation, String> permissionNameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, name);
		ImmutableTriple<String, ClientOperation, String> ridConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, String.valueOf(rid));
		final RolePermission entityByName = rolePermissionervice.searchOne(permissionNameConstraint, ridConstraint);
		if (entityByName == null) {
			final RolePermission entity = new RolePermission();
			RolePermissionPK id = new RolePermissionPK();
			id.setPermission(name);
			id.setRid(rid);
			entity.setId(id);
			entity.setModule(module);
			rolePermissionervice.create(entity);
		}
	}

	/**
	 * Role
	 * 创建角色
	 */
	private void createRoles() {
/*		final RolePermission canRolePermissionRead = RolePermissionervice.findByName(RolePermission.CAN_RolePermission_READ);
		final RolePermission canRolePermissionWrite = RolePermissionervice.findByName(RolePermission.CAN_RolePermission_WRITE);
		final RolePermission canRoleRead = RolePermissionervice.findByName(RolePermission.CAN_ROLE_READ);
		final RolePermission canRoleWrite = RolePermissionervice.findByName(RolePermission.CAN_ROLE_WRITE);
		final RolePermission canUserRead = RolePermissionervice.findByName(RolePermission.CAN_USER_READ);
		final RolePermission canUserWrite = RolePermissionervice.findByName(RolePermission.CAN_USER_WRITE);

		Preconditions.checkNotNull(canRolePermissionRead);
		Preconditions.checkNotNull(canRolePermissionWrite);
		Preconditions.checkNotNull(canRoleRead);
		Preconditions.checkNotNull(canRoleWrite);
		Preconditions.checkNotNull(canUserRead);
		Preconditions.checkNotNull(canUserWrite);

		createRoleIfNotExisting(Roles.ADMINISTRATOR, Sets.<RolePermission> newHashSet(canUserRead, canUserWrite, canRoleRead, canRoleWrite, canRolePermissionRead, canRolePermissionWrite));
		*/
		createRoleIfNotExisting(Roles.ADMINISTRATOR, new ArrayList<RolePermission>(){{  }});
		
	}

	/**
	 * 如果不存在,创建角色
	 * @param name
	 * @param RolePermission
	 */
	final void createRoleIfNotExisting(final String name, final List<RolePermission> RolePermission) {
		ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, name);
		final Role entityByName = roleService.searchOne(nameConstraint);
		if (entityByName == null) {
			final Role entity = new Role();
			entity.setName(name);
			//entity.setRolePermissions(RolePermission);
			roleService.create(entity);
		}
	}

	/**
	 * User/User
	 * 创建管理员
	 */
	final void createUsers() {
		ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, Roles.ADMINISTRATOR);
		final Role roleAdmin = roleService.searchOne(nameConstraint);
		createUserIfNotExisting(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS, new ArrayList<Role>(){{ add(roleAdmin); }});
	}

	/**
	 * 如果不存在创建管理员
	 * @param loginName
	 * @param pass
	 * @param roles
	 */
	final void createUserIfNotExisting(final String loginName, final String pass, final List<Role> roles) {
		ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, loginName);
		final User entityByName = userService.searchOne(nameConstraint);
		if (entityByName == null) {
			final User entity = new User();
			entity.setName(loginName);
			entity.setPass(pass);
			entity.setRoles(roles);
			entity.setCreated(new Date());
			entity.setTheme(WebConstants.ORIGINAL_DEFAULT_THEME_NAME);
			entity.setStatus(Byte.parseByte("1"));
			userService.create(entity);
		}
	}

}
