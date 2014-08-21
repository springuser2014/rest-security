package net.zzh.sec.persistence.setup;

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
import net.zzh.common.spring.CommonSpringProfileUtil;
import net.zzh.sec.model.Role;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.model.Test;
import net.zzh.sec.model.Test_;
import net.zzh.sec.persistence.service.IRoleService;
import net.zzh.sec.persistence.service.IUserService;
import net.zzh.sec.util.SecurityConstants.Privileges;

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
	private IUserService principalService;

	@Autowired
	private IRoleService roleService;

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
			createPrivileges();
			createRoles();
			createPrincipals();
			
			//System.out.println(logger.isDebugEnabled());
			//procService.delete(0);
			
			//List list = privilegeService.searchAll("SELECT priv_id,description,name FROM rest.privilege");
			//System.out.println(list.size());
			//Page page = persistenceService.findPaginated(1, 10, "select name,(select ROLE_ID from Role_Privilege where a.PRIV_ID = PRIV_ID) from Privilege a");
			//Page page = persistenceService.findPaginated(1, 10, "select a.username,(select b.s0204 from S02 b where b.s0200 in (select c.s0301 from S03 c where c.s0302 = a.id)) from Jiveuser a");

			/*
			 *  success
			 */

			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Test> query = builder.createQuery(Test.class);
			Root<Test> root = query.from(Test.class);

			Predicate hasBirthday = builder.equal(root.get(Test_.idtest), 1);
			//Predicate isLongTermTest = builder.lessThan(root.get(Test_.createdAt), today.minusYears(2); 
			query.where(builder.and(hasBirthday));
			List list = em.createQuery(query.select(root)).getResultList();
			System.out.println(list.size());
			
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
		createPrivilegeIfNotExisting(Privileges.CAN_PRIVILEGE_READ);
		createPrivilegeIfNotExisting(Privileges.CAN_PRIVILEGE_WRITE);

		createPrivilegeIfNotExisting(Privileges.CAN_ROLE_READ);
		createPrivilegeIfNotExisting(Privileges.CAN_ROLE_WRITE);

		createPrivilegeIfNotExisting(Privileges.CAN_USER_READ);
		createPrivilegeIfNotExisting(Privileges.CAN_USER_WRITE);
	}

	/**
	 * 如果不存在,创建权限
	 * @param name
	 */
	final void createPrivilegeIfNotExisting(final String name) {
		/*final Privilege entityByName = privilegeService.findByName(name);
		if (entityByName == null) {
			final Privilege entity = new Privilege(name);
			privilegeService.create(entity);
		}*/
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
	}

	/**
	 * 如果不存在,创建角色
	 * @param name
	 * @param privileges
	 */
	final void createRoleIfNotExisting(final String name, final Set<RolePermission> privileges) {
		/*final Role entityByName = roleService.findByName(name);
		if (entityByName == null) {
			final Role entity = new Role(name);
			entity.setPrivileges(privileges);
			roleService.create(entity);
		}*/
	}

	/**
	 * Principal/User
	 * 创建管理员
	 */
	final void createPrincipals() {
		/*final Role roleAdmin = roleService.findByName(Roles.ROLE_ADMIN);

		// createPrincipalIfNotExisting(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASS, Sets.<Role> newHashSet(roleAdmin));
		createPrincipalIfNotExisting(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS, Sets.<Role> newHashSet(roleAdmin));
		*/
	}

	/**
	 * 如果不存在创建管理员
	 * @param loginName
	 * @param pass
	 * @param roles
	 */
	final void createPrincipalIfNotExisting(final String loginName, final String pass, final Set<Role> roles) {
		/*final Principal entityByName = principalService.findByName(loginName);
		if (entityByName == null) {
			final Principal entity = new Principal(loginName, pass, roles);
			principalService.create(entity);
		}*/
	}

}
