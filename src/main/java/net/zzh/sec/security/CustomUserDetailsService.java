/**
 * 
 */
package net.zzh.sec.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.zzh.common.persistence.service.IPersistenceService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.util.SearchField;
import net.zzh.sec.model.User;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.model.Role;
import net.zzh.sec.persistence.service.IUserService;

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
	private IUserService userService;

	@Autowired
	private IPersistenceService persistenceService;
	
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
		
		ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), ClientOperation.EQ, username);
		User user = userService.searchOne(nameConstraint);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username was not found: " + username);
		}
		final List<Role> rolesOfUser = user.getRoles();
		final List<RolePermission> privileges = new ArrayList<RolePermission>();
		for (final Role roleOfUser : rolesOfUser) {
			privileges.addAll(roleOfUser.getRolePermissions());
		}
		final Function<Object, String> toStringFunction = Functions.toStringFunction();
		final Collection<String> rolesToString = Collections2.transform(privileges, toStringFunction);
		final String[] roleStringsAsArray = rolesToString.toArray(new String[rolesToString.size()]);
		final List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(roleStringsAsArray);

		System.out.println("loadUserByUsername - success");
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountIsEnabled = true;
		
		System.out.println(user.getName() + ", " +
				user.getPass().toLowerCase() + ", " +
				accountIsEnabled + ", " +
				accountNonExpired + ", " +
				credentialsNonExpired + ", " +
				(user.getStatus() == 1 ? false : true) + ", " +
				auths);
		
		return new org.springframework.security.core.userdetails.User(
				user.getName(),
				user.getPass().toLowerCase(),
				accountIsEnabled,
				accountNonExpired,
				credentialsNonExpired,
				(user.getStatus() == 1 ? false : true),
				auths);
	}
}
