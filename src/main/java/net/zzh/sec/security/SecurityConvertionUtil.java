package net.zzh.sec.security;

import java.util.Collection;
import java.util.Set;

import net.zzh.sec.model.RolePermission;
import net.zzh.sec.model.Role;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

public final class SecurityConvertionUtil {

    private SecurityConvertionUtil() {
        throw new AssertionError();
    }

    // API

    public static Set<RolePermission> convertRolesToRolePermissions(final Iterable<Role> roles) {
        final Set<RolePermission> RolePermissions = Sets.<RolePermission> newHashSet();
        for (final Role roleOfUser : roles) {
            RolePermissions.addAll(roleOfUser.getRolePermissions());
        }
        return RolePermissions;
    }

    public static Collection<String> convertRolePermissionsToRolePermissionNames(final Collection<RolePermission> RolePermissions) {
        final Function<Object, String> toStringFunction = Functions.toStringFunction();
        return Collections2.transform(RolePermissions, toStringFunction);
    }

    public static Collection<String> convertRolesToRolePermissionNames(final Iterable<Role> roles) {
        final Set<RolePermission> RolePermissions = convertRolesToRolePermissions(roles);
        return convertRolePermissionsToRolePermissionNames(RolePermissions);
    }

}
