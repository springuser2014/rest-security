package net.zzh.sec.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;

import net.zzh.common.web.WebConstants;
import net.zzh.sec.security.CookieAuthenticationFilter;
import net.zzh.sec.security.CookieLogoutHandler;
import net.zzh.sec.security.CookieService;
import net.zzh.sec.security.MyThemeResolver;
import net.zzh.sec.security.MyUserDetailsService;
import net.zzh.sec.security.SecurityLoginFailureHandler;
import net.zzh.sec.security.SecurityLoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

//@Configuration
//@ComponentScan("net.zzh.sec.security, net.zzh.sec.web.servlet")
//@ImportResource({ "classpath*:*secSecurityConfig.xml" })
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class Copy_2_of_SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private ApplicationContext context;
	
	private static final String ANON_PROVIDER_KEY = "9000234288201316478";
	
	static AccessDecisionManager ACCESS_DECISION_MGR;
	
	public Copy_2_of_SecurityConfig() {
		super();
	}

	// API
	@Override
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
	/**
	 * Ignore any request that starts with "/resources/".
	 * This is similar to configuring http@security=none when using the XML namespace configuration. 
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			//Spring Security ignores request to static resources such as CSS or JS files.
			.ignoring()
				.antMatchers(
						"/favicon.ico",
						"/css/**",
						"/fonts/**",
						"/img/**",
						"/js/**",
						"/ui/**"
				);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeUrls()
				//Anyone can access the urls
				.antMatchers(
						//WebConstants.PATH_SEP,
						WebConstants.PATH_SIGNUP,
						WebConstants.PATH_SIGNIN,
						WebConstants.PATH_SIGNOUT,
						WebConstants.PATH_ABOUT//,
						//"/pages/**"
				).permitAll()
				//The rest of the our application is protected.
				//.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated() // 所有其他的URL都需要用户进行验证
				.and()
			/*
			.exceptionHandling()
				.accessDeniedPage(WebConstants.PATH_DENIED)
				// this entry point handles when you request a protected page
				// and
				// you are not yet authenticated
				.authenticationEntryPoint(digestEntryPoint())
				.and()
			*/
			//Configures the logout function
			.logout()
				.deleteCookies("JSESSIONID")
				.logoutUrl(WebConstants.PATH_SIGNOUT)
				.logoutSuccessUrl(WebConstants.PATH_SIGNIN)
				.and()
			
			//Configures form login
			.formLogin()
				// login-processing-url仅仅处理HTTP POST
				// login-page仅仅通过HTTP GET进入
				.loginPage(WebConstants.PATH_SIGNIN)
				.loginProcessingUrl(WebConstants.PATH_SIGNIN)
				.failureUrl(WebConstants.PATH_SIGNIN + "?param.error=bad_credentials")
				.defaultSuccessUrl(WebConstants.PATH_SEP)
				.usernameParameter("u")
				.passwordParameter("p")
				.permitAll()
				.and()
			
			/*
			.formLogin()
				// login-processing-url仅仅处理HTTP POST
				// login-page仅仅通过HTTP GET进入
				.loginPage(WebConstants.PATH_SIGNIN)
				.failureUrl(WebConstants.PATH_SIGNIN + "?param.error=bad_credentials")
				.defaultSuccessUrl(WebConstants.PATH_HOME)
				.usernameParameter("u")
				.passwordParameter("p")
				.permitAll()
				.and()
				*/
			/*.httpBasic()
				.authenticationEntryPoint(loginAuthEntryPoint())
				.and()*/
			
			//.addFilterAfter(digestAuthenticationFilter(), BasicAuthenticationFilter.class)
			//.addFilter(getFilterChainProxy())
			//.addFilter(getUserNamePasswordAuthenticationFilter())
			/*
			.rememberMe()
				.rememberMeServices(rememberMeServices())
				.key("password")
				.and()
			*/
			.setSharedObject(ApplicationContext.class, context);
	}
	
	/*@Override
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(getMyUserDetailsService())
			.passwordEncoder(passwordEncoder());
	}*/
	
	@Bean
	public RememberMeServices rememberMeServices() {
		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("password", userDetailsService());
		rememberMeServices.setCookieName("cookieName");
		rememberMeServices.setParameter("rememberMe");
		return rememberMeServices;
	}
	
	/*@Bean
	public DigestAuthenticationEntryPoint digestEntryPoint() {
		
		DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
		digestEntryPoint.setKey("acegi");
		digestEntryPoint.setRealmName("Contacts Realm via Digest Authentication");
		
		return digestEntryPoint;
	}*/
	
	@Bean
	public LoginUrlAuthenticationEntryPoint loginAuthEntryPoint() {
		
		LoginUrlAuthenticationEntryPoint loginAuthEntryPoint = new LoginUrlAuthenticationEntryPoint(WebConstants.PATH_SIGNIN);
		return loginAuthEntryPoint;
	}

	/**
	 * Responsible for extracting User/Group information
	 * 
	 * @return MyUserDetailsService
	 */
	/*@Bean
	public MyUserDetailsService getMyUserDetailsService() {
		return new MyUserDetailsService();
	}*/
	/**
	 * @return Cookie Service for management of Cookies
	 */
	/*@Bean
	public CookieService getCookieService() {
		return new CookieService.Impl();
	}*/

	/**
	 * @return The Authentication Manager to user
	 * @throws Exception 
	 */
	/*private ProviderManager getAuthenticationManager() throws Exception {
		List<AuthenticationProvider> authManagers = new ArrayList<AuthenticationProvider>();
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();

		daoProvider.setUserDetailsService(getMyUserDetailsService());

		authManagers.add(daoProvider);

		authManagers.add(new AnonymousAuthenticationProvider(ANON_PROVIDER_KEY));

		ProviderManager providerManager = new ProviderManager(authManagers);

		return providerManager;
	}*/
/*
	@Bean(name = "springSecurityFilterChain")
	public FilterChainProxy getFilterChainProxy() {
		SecurityFilterChain chain = new SecurityFilterChain() {

			@Override
			public boolean matches(HttpServletRequest request) {
				// All goes through here
				return true;
			}

			@Override
			public List<Filter> getFilters() {
				List<Filter> filters = new ArrayList<Filter>();

				try {
					filters.add(getCookieAuthenticationFilter());
					filters.add(getLogoutFilter());
					filters.add(getUserNamePasswordAuthenticationFilter());
					filters.add(getSecurityContextHolderAwareRequestFilter());
					filters.add(getAnonymousAuthenticationFilter());
					filters.add(getExceptionTranslationFilter());
					filters.add(getFilterSecurityInterceptor());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return filters;
			}
		};

		return new FilterChainProxy(chain);
	}
*/
	// Filters
	
	/*public DigestAuthenticationFilter digestAuthenticationFilter() {
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
		digestAuthenticationFilter.setUserDetailsService(getMyUserDetailsService());
		return digestAuthenticationFilter;
	}*/

	/**
	 * @return Filter that checks if a cookie exists for the user, if so loads
	 *         the user details and sets the authentication context
	 */
	/*private CookieAuthenticationFilter getCookieAuthenticationFilter() {
		return new CookieAuthenticationFilter(getMyUserDetailsService(), getCookieService());
	}*/

	/**
	 * @return Filter for handling logout
	 */
	/*private LogoutFilter getLogoutFilter() {
		return new LogoutFilter(WebConstants.PATH_SEP, new CookieLogoutHandler(getCookieService()));
	}*/

	/**
	 * @return Filter for authentication the user.
	 * @throws Exception 
	 */
	/*private UsernamePasswordAuthenticationFilter getUserNamePasswordAuthenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();

		filter.setAllowSessionCreation(false);
		filter.setAuthenticationManager(getAuthenticationManager());
		//filter.setAuthenticationSuccessHandler(new AuthSuccessHandler(getCookieService()));
		//filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(WebConstants.PATH_SIGNIN + "?param.error=bad_credentials"));
		filter.setAuthenticationSuccessHandler(new SecurityLoginSuccessHandler());
		filter.setAuthenticationFailureHandler(new SecurityLoginFailureHandler());
		
		filter.setFilterProcessesUrl("/j_spring_security_check");

		return filter;
	}*/

	/**
	 * @return Filter that manages the ROLE prefix
	 * @throws ServletException 
	 */
	private SecurityContextHolderAwareRequestFilter getSecurityContextHolderAwareRequestFilter() throws ServletException {
		SecurityContextHolderAwareRequestFilter filter = new SecurityContextHolderAwareRequestFilter();
		filter.afterPropertiesSet();
		filter.setRolePrefix("ROLE_");
		return filter;
	}

	/**
	 * @return Anonymous Authentication Filter for IS_AUTHENTICATED_ANONYMOUSLY
	 */
	private AnonymousAuthenticationFilter getAnonymousAuthenticationFilter() {
		return new AnonymousAuthenticationFilter("ClientApplication", "anonymousUser", AuthorityUtils.createAuthorityList(ANONYMOUS));
	}

	/**
	 * @return Exception Translation filter
	 */
	private ExceptionTranslationFilter getExceptionTranslationFilter() {
		LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint(WebConstants.PATH_SIGNIN);
		AccessDeniedHandlerImpl errorHandler = new AccessDeniedHandlerImpl();
		errorHandler.setErrorPage(WebConstants.PATH_SIGNIN + "?param.error=bad_credentials");

		ExceptionTranslationFilter filter = new ExceptionTranslationFilter(entryPoint, new NullRequestCache());

		filter.setAccessDeniedHandler(errorHandler);

		return filter;
	}

	private static final String ANONYMOUS = "IS_AUTHENTICATED_ANONYMOUSLY";

	/**
	 * Performs security handling of HTTP resources via a filter implementation
	 * 
	 * @return A FilterSecurityInterceptor
	 * @throws Exception 
	 */
	/*private FilterSecurityInterceptor getFilterSecurityInterceptor() throws Exception {
		FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

		// Map anonymous access URL's
		Collection<ConfigAttribute> configAttributeCollection = new ArrayList<ConfigAttribute>();

		configAttributeCollection.add(new org.springframework.security.access.SecurityConfig(ANONYMOUS));

		requestMap.put(new AntPathRequestMatcher(WebConstants.PATH_SIGNIN+"*"), configAttributeCollection);
		requestMap.put(new AntPathRequestMatcher("/favicon.ico"), configAttributeCollection);
		requestMap.put(new AntPathRequestMatcher("/css*"), configAttributeCollection);
		requestMap.put(new AntPathRequestMatcher("/fonts*"), configAttributeCollection);
		requestMap.put(new AntPathRequestMatcher("/js*"), configAttributeCollection);
		requestMap.put(new AntPathRequestMatcher("/img*"), configAttributeCollection);
		requestMap.put(new AntPathRequestMatcher("/ui*"), configAttributeCollection);

		// Map /* for all user based resources
		Collection<ConfigAttribute> configAttributeCollection2 = new ArrayList<ConfigAttribute>();
		configAttributeCollection2.add(new org.springframework.security.access.SecurityConfig("ROLE_USER"));

		requestMap.put(new AntPathRequestMatcher(WebConstants.PATH_SEP+"*"), configAttributeCollection2);

		FilterInvocationSecurityMetadataSource metaSource = new DefaultFilterInvocationSecurityMetadataSource(requestMap);

		interceptor.setSecurityMetadataSource(metaSource);

		// Set auth manager for the filter
		interceptor.setAuthenticationManager(getAuthenticationManager());

		// Access Decision Manager
		@SuppressWarnings("rawtypes")
		List<AccessDecisionVoter> accessDecisionVoters = new ArrayList<AccessDecisionVoter>();

		accessDecisionVoters.add(new RoleVoter());
		accessDecisionVoters.add(new AuthenticatedVoter());
		AffirmativeBased decisionManager = new AffirmativeBased(accessDecisionVoters);

		interceptor.setAccessDecisionManager(decisionManager);

		return interceptor;
	}*/
	/*
	private UsernamePasswordAuthenticationFilter getUserNamePasswordAuthenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();

		filter.setAllowSessionCreation(false);
		filter.setAuthenticationManager(authenticationManagerBean());
		
		// Set the Auth Success handler
		SecurityLoginSuccessHandler ajaxSecurityLoginSuccessHandler = new SecurityLoginSuccessHandler();
		ajaxSecurityLoginSuccessHandler.setDefaultTargetUrl(WebConstants.PATH_SIGNIN);
		filter.setAuthenticationSuccessHandler(ajaxSecurityLoginSuccessHandler);

		// Set the Auth Failure handler
		SecurityLoginFailureHandler ajaxSecurityLoginFailureHandler = new SecurityLoginFailureHandler();
		ajaxSecurityLoginFailureHandler.setDefaultFailureUrl(WebConstants.PATH_SIGNIN);
		filter.setAuthenticationFailureHandler(ajaxSecurityLoginFailureHandler);
		
		filter.setAuthenticationFailureHandler(new SecurityLoginFailureHandler());

		filter.setFilterProcessesUrl("/j_spring_security_check");
		
		return filter;
	}*/
/*
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new MyUserDetailsService();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	*/
	
	@Bean
	public LocaleResolver localeResolver() {

		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("zh_CN"));
		return cookieLocaleResolver;
	}

	@Bean
	public ThemeSource themeSource() {
		
		ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
		themeSource.setBasenamePrefix(WebConstants.THEME_PREFIX);
		return themeSource;
	}

	@Bean
	public ThemeResolver themeResolver() {
		MyThemeResolver myThemeResolver = new MyThemeResolver();
		myThemeResolver.setDefaultThemeName(WebConstants.ORIGINAL_DEFAULT_THEME_NAME);
		return myThemeResolver;
	}
	
	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
		//messageSource.setBasename("i18n/messages");
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(0);
		return messageSource;
	}
}
