package net.zzh.sec.persistence.setup;

import java.util.Date;
import java.util.List;
import java.util.Set;

import net.zzh.common.event.BeforeSetupEvent;
import net.zzh.common.persistence.service.IPersistenceService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.spring.CommonSpringProfileUtil;
import net.zzh.common.util.SearchField;
import net.zzh.common.web.WebConstants;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.model.Role;
import net.zzh.sec.model.User;
import net.zzh.sec.persistence.service.IRolePermissionService;
import net.zzh.sec.persistence.service.IRoleService;
import net.zzh.sec.persistence.service.IUserService;
import net.zzh.sec.util.SecurityConstants;
import net.zzh.sec.util.SecurityConstants.Permissions;
import net.zzh.sec.util.SecurityConstants.Roles;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
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
			createRolePermission();
			createRoles();
			createUsers();
			
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
		createRolePermissionIfNotExisting(Permissions.CAN_PERMISSION_READ);
		createRolePermissionIfNotExisting(Permissions.CAN_PERMISSION_WRITE);

		createRolePermissionIfNotExisting(Permissions.CAN_ROLE_READ);
		createRolePermissionIfNotExisting(Permissions.CAN_ROLE_WRITE);

		createRolePermissionIfNotExisting(Permissions.CAN_USER_READ);
		createRolePermissionIfNotExisting(Permissions.CAN_USER_WRITE);
	}

	/**
	 * 如果不存在,创建权限
	 * @param name
	 */
	final void createRolePermissionIfNotExisting(final String name) {
		final RolePermission entityByName = RolePermissionervice.findByName(name);
		if (entityByName == null) {
			final RolePermission entity = new RolePermission(name);
			RolePermissionervice.create(entity);
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

		createRoleIfNotExisting(Roles.ROLE_ADMIN, Sets.<RolePermission> newHashSet(canUserRead, canUserWrite, canRoleRead, canRoleWrite, canRolePermissionRead, canRolePermissionWrite));
		*/
	}

	/**
	 * 如果不存在,创建角色
	 * @param name
	 * @param RolePermission
	 */
	final void createRoleIfNotExisting(final String name, final Set<RolePermission> RolePermission) {
		/*final Role entityByName = roleService.findByName(name);
		if (entityByName == null) {
			final Role entity = new Role(name);
			entity.setRolePermission(RolePermission);
			roleService.create(entity);
		}*/
	}

	/**
	 * User/User
	 * 创建管理员
	 */
	final void createUsers() {
		ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, Roles.ROLE_ADMIN);
		final Role roleAdmin = roleService.searchOne(nameConstraint);
		createUserIfNotExisting(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS, Sets.<Role> newHashSet(roleAdmin));
	}

	/**
	 * 如果不存在创建管理员
	 * @param loginName
	 * @param pass
	 * @param roles
	 */
	final void createUserIfNotExisting(final String loginName, final String pass, final Set<Role> roles) {
		ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, loginName);
		final User entityByName = userService.searchOne(nameConstraint);
		if (entityByName == null) {
			final User entity = new User();
			entity.setName(loginName);
			entity.setPass(pass);
			entity.setRoles(roles);
			entity.setTheme(WebConstants.ORIGINAL_DEFAULT_THEME_NAME);
			entity.setCreated((int) System.currentTimeMillis());
			userService.create(entity);
		}
	}

}
