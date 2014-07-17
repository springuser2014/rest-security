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

    // Modules

    public static final class Modules {
        public static final String SYSTEM = "system";
        public static final String USER = "user";
        
    }
    
    // Permissions

    public static final class UserPermissions {
        public static final String ADMINISTER_PERMISSIONS = "administer permissions"; // 管理权限
        public static final String ADMINISTER_USERS = "administer users"; //管理用户
        public static final String ACCESS_USER_PROFILES = "access user profiles"; //查看用户资料
        public static final String CHANGE_OWN_USERNAME = "change own username"; //更改自己的用户名
        
    }
    
    public static final class SystemPermissions {
        public static final String ACCESS_ADMINISTRATION_PAGES = "access administration pages"; // 使用管理页面和帮助
        public static final String BLOCK_IP_ADDRESSES = "block IP addresses"; // 阻止ip地址
        public static final String ADMINISTER_MODULES = "administer modules"; // 管理模块
        public static final String ADMINISTER_SITE_CONFIGURATION = "administer site configuration"; // 站点配置管理
        public static final String ADMINISTER_THEMES = "administer themes"; // 管理主题
        public static final String VIEW_THE_ADMINISTRATION_THEME = "view the administration theme"; // 查看管理员主题
        public static final String ACCESS_SITE_REPORTS = "access site reports"; // 查看站点报告
        public static final String ACCESS_SITE_IN_MAINTENANCE_MODE = "access site in maintenance mode"; //使用站点维护模式
        
    }

    public static final class Roles {

        /** A placeholder role for administrator. 管理员*/
        public static final String ADMINISTRATOR = "administrator";
        /** A placeholder role for enduser. 终端用户 */
        public static final String ENDUSER = "ENDUSER";
        // 匿名用户
        public static final String ANONYMOUS_USER = "anonymous user";
        // 认证用户
        public static final String AUTHENTICATED_USER = "authenticated user";

    }

    private SecurityConstants() {
        throw new AssertionError();
    }

}
