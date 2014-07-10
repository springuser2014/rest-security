package net.zzh.sec.util;

public final class SecurityConstants {

    /**
     * Permissions <br/>
     * - note: the fact that these Privileges are prefixed with `ROLES` is a Spring convention (which can be overriden if needed)
     */
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASS = "adminpass";
    public static final String ADMIN_EMAIL = "admin@localhost.com";

    public static final String NAME = ADMIN_USERNAME;
    public static final String PASS = ADMIN_PASS;
    public static final String EMAIL = ADMIN_EMAIL;

    // Permissions

    public static final class Permissions {

        // User
        public static final String CAN_USER_READ = "ROLE_USER_READ";
        public static final String CAN_USER_WRITE = "ROLE_USER_WRITE";

        // Role
        public static final String CAN_ROLE_READ = "ROLE_ROLE_READ";
        public static final String CAN_ROLE_WRITE = "ROLE_ROLE_WRITE";

        // Permission
        public static final String CAN_PERMISSION_READ = "ROLE_PERMISSION_READ";
        public static final String CAN_PERMISSION_WRITE = "ROLE_PERMISSION_WRITE";

    }

    public static final class Roles {

        /** A placeholder role for administrator. */
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        /** A placeholder role for enduser. */
        public static final String ROLE_ENDUSER = "ROLE_ENDUSER";

    }

    private SecurityConstants() {
        throw new AssertionError();
    }

}
