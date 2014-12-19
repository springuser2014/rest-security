package net.zzh.sec.web.controller;

import java.util.Collection;

import net.zzh.common.security.SpringSecurityUtil;
import net.zzh.sec.model.Role;
import net.zzh.sec.model.dto.UserProfile;
import net.zzh.sec.web.common.UriMappingConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * - note: this controller will start working with the User model and, if necessary, will move to a Authentication resource (which is the way it should work)
 */
@Controller
public class AuthenticationController {

    public AuthenticationController() {
        super();
    }

    // API

//    @RequestMapping(method = RequestMethod.GET, value = UriMappingConstants.AUTHENTICATION)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public Profile createAuthentication() {
//    	System.out.println(UriMappingConstants.AUTHENTICATION);
//        final Authentication authenticationInSpring = SpringSecurityUtil.getCurrentAuthentication();
//
//        final Function<GrantedAuthority, Privilege> springAuthorityToPrivilegeFunction = new Function<GrantedAuthority, Privilege>() {
//            public final Privilege apply(final GrantedAuthority springAuthority) {
//                return new Privilege(springAuthority.getAuthority());
//            }
//        };
//        final Collection<Privilege> privileges = Collections2.transform(authenticationInSpring.getAuthorities(), springAuthorityToPrivilegeFunction);
//        final Role defaultRole = new Role("defaultRole", Sets.<Privilege> newHashSet(privileges));
//
//        final Profile authenticationResource = new Profile(authenticationInSpring.getName(), (String) authenticationInSpring.getCredentials(), Sets.<Role> newHashSet(defaultRole));
//        return authenticationResource;
//    }

}
