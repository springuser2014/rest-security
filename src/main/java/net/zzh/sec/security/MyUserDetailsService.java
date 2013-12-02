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

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * Database user authentication service.
 */
@Service
@Component
public final class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IPrincipalService principalService;

	@Autowired
	private IPersistenceService persistenceService;

    public MyUserDetailsService() {
        super();
    }

    // API - public

    /**
     * Loads the user from the datastore, by it's user name <br>
     */
    public final UserDetails loadUserByUsername(final String username) {
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
		return new User(principal.getName(), principal.getPassword(), auths);

/*
        //return new User(username, "123145", auths);
        
        boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		User signedUser = new User(username, "123145"
				.toLowerCase(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked,
				getAuthorities(1));

		return signedUser;*/
    }

	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical
	 * role
	 * 
	 * @param role
	 *            the numerical role
	 * @return a collection of {@link GrantedAuthority

	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	/**
	 * Converts a numerical role to an equivalent list of roles
	 * 
	 * @param role
	 *            the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();

		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");

		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}

		return roles;
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * 
	 * @param roles
	 *            {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}
