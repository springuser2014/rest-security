/**
 * 
 */
package net.zzh.sec.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.zzh.common.persistence.service.IPersistenceService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.util.SearchField;
import net.zzh.sec.model.Users;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.model.Role;
import net.zzh.sec.model.Users_;
import net.zzh.sec.persistence.service.IUsersService;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;

/**
 * @author zhenhuazhao
 *
 */
@Service
//@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsersService usersService;

	@Autowired
	private IPersistenceService persistenceService;

	@Autowired(required=false)
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 
	 */
	public CustomUserDetailsService() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername - check");

		Preconditions.checkNotNull(username);

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Users> query = builder.createQuery(Users.class);
		Root<Users> root = query.from(Users.class);

		Predicate hasName = builder.equal(root.get(Users_.name), username);
		query.where(builder.and(hasName));
		List list = em.createQuery(query.select(root)).getResultList();
		Users user;
		if(list.isEmpty()) {
			throw new UsernameNotFoundException("Username was not found: " + username);
		} else {
			user = (Users) list.get(0);
		}
		
		final List<Role> rolesOfUser = user.getRoles();
		final List<RolePermission> privileges = new ArrayList<RolePermission>();
		for (final Role roleOfUser : rolesOfUser) {
			//privileges.addAll(role.getRolePermissions());
			privileges.add(roleOfUser.getRolePermission());
		}
		final Function<Object, String> toStringFunction = Functions.toStringFunction();
		final Collection<String> rolesToString = Collections2.transform(privileges, toStringFunction);
		final String[] roleStringsAsArray = rolesToString.toArray(new String[rolesToString.size()]);
		final List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(roleStringsAsArray);

		System.out.println("loadUserByUsername - success");
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountIsEnabled = true;
		/*
		System.out.println(user.getName() + ", " +
				user.getPass().toLowerCase() + ", " +
				accountIsEnabled + ", " +
				accountNonExpired + ", " +
				credentialsNonExpired + ", " +
				(user.getStatus() == 0 ? false : true) + ", " +
				auths);
		*/
		return new org.springframework.security.core.userdetails.User(
				user.getName(),
				user.getPass().toLowerCase(),
				accountIsEnabled,
				accountNonExpired,
				credentialsNonExpired,
				(user.getStatus() == 0 ? false : true),
				auths);
	}
}
