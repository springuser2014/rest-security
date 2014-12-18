package net.zzh.sec.util;

public final class SecurityConstants {

    /**
     * Privileges <br/>
     * - note: the fact that these Privileges are prefixed with `ROLES` is a Spring convention (which can be overriden if needed)
     */
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASS = "adminpass";
    public static final String ADMIN_EMAIL = "admin@fake.com";

    public static final String NAME = ADMIN_USERNAME;
    public static final String PASS = ADMIN_PASS;
    public static final String EMAIL = ADMIN_EMAIL;

    // modules
    
    public static final class Modules {
    	public static final String SYSTEM = "SYSTEM";
    	public static final String USER = "USER";
    }
    
    // privileges

    public static final class Privileges {

        // User module
        public static final String ACCESS_USER_PROFILES = "access user profiles";
        public static final String CHANGE_OWN_USERNAME = "change own username";
        public static final String ADMINISTER_PERMISSIONS = "administer permissions";
        public static final String ADMINISTER_USERS = "administer users";
        
        // System module
        public static final String ACCESS_ADMINISTRATION_PAGES = "access administration pages";
        public static final String ACCESS_SITE_IN_MAINTENANCE_MODE = "access site in maintenance mode";
        public static final String ACCESS_SITE_REPORTS = "access site reports";
        public static final String ADMINISTER_MODULES = "administer modules";
        public static final String ADMINISTER_SITE_CONFIGURATION = "administer site configuration";
        public static final String ADMINISTER_THEMES = "administer themes";
        public static final String BLOCK_IP_ADDRESSES = "block IP addresses";

    }

    public static final class Roles {

        /*
         * 管理员
         * A placeholder role for administrator.
         */
        public static final String ADMINISTRATOR = "administrator";
        /*
         * 认证用户
         * A placeholder role for enduser.
         */
        public static final String AUTHENTICATED_USER = "authenticated user";

    }

    private SecurityConstants() {
        throw new AssertionError();
    }

}
