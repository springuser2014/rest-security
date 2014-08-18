package net.zzh.sec.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.zzh.sec.model.Role;
import net.zzh.sec.model.RolePermission;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

public final class SecurityConvertionUtil {

    private SecurityConvertionUtil() {
        throw new AssertionError();
    }

    // API

    public static List<RolePermission> convertRolesToPermissions(final Iterable<Role> roles) {
        final List<RolePermission> permissions = new ArrayList<RolePermission>();
        for (final Role roleOfUser : roles) {
        	permissions.addAll(roleOfUser.getRolePermissions());
        }
        return permissions;
    }

    public static Collection<String> convertPrivilegesToPrivilegeNames(final Collection<RolePermission> permissions) {
        final Function<Object, String> toStringFunction = Functions.toStringFunction();
        return Collections2.transform(permissions, toStringFunction);
    }

    public static Collection<String> convertRolesToPrivilegeNames(final Iterable<Role> roles) {
        final List<RolePermission> permissions = convertRolesToPermissions(roles);
        return convertPrivilegesToPrivilegeNames(permissions);
    }

}
