/**
 * 
 */
package net.zzh.sec.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.zzh.common.persistence.service.IPersistenceService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.util.SearchField;
import net.zzh.sec.model.Principal;
import net.zzh.sec.model.Privilege;
import net.zzh.sec.model.Role;
import net.zzh.sec.persistence.service.IPrincipalService;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * @author zhenhuazhao
 *
 */
@Service
@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IPrincipalService principalService;

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
		Principal principal = principalService.searchOne(nameConstraint);
		
		if (principal == null) {
			throw new UsernameNotFoundException("Username was not found: " + username);
		}
		final Set<Role> rolesOfUser = principal.getRoles();
		final Set<Privilege> privileges = Sets.newHashSet();
		for (final Role roleOfUser : rolesOfUser) {
			privileges.addAll(roleOfUser.getPrivileges());
		}
		final Function<Object, String> toStringFunction = Functions.toStringFunction();
		final Collection<String> rolesToString = Collections2.transform(privileges, toStringFunction);
		final String[] roleStringsAsArray = rolesToString.toArray(new String[rolesToString.size()]);
		final List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(roleStringsAsArray);

		System.out.println("loadUserByUsername - success");
		//return new User(principal.getName(), principal.getPassword(), auths);
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountIsEnabled = true;

		System.out.println(principal.getName() + ", " +
				principal.getPassword().toLowerCase() + ", " +
				accountIsEnabled + ", " +
				accountNonExpired + ", " +
				credentialsNonExpired + ", " +
				(principal.getLocked() == true ? false : true) + ", " +
				auths);
		
		return new User(
				principal.getName(),
				principal.getPassword().toLowerCase(),
				accountIsEnabled,
				accountNonExpired,
				credentialsNonExpired,
				(principal.getLocked() == true ? false : true),
				auths);
	}
}
