package net.zzh.sec.spring;

import net.zzh.common.web.WebConstants;
import net.zzh.sec.security.MyUserDetailsService;
import net.zzh.sec.web.servlet.MyThemeResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
@ComponentScan("net.zzh.sec.security, net.zzh.sec.web.servlet")
//@ImportResource({ "classpath*:*secSecurityConfig.xml" })
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ApplicationContext context;
	
	private MyUserDetailsService myUserDetailsService = new MyUserDetailsService();
	
	static AccessDecisionManager ACCESS_DECISION_MGR;
	public SecurityConfig() {
		super();
	}

	// API
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
						WebConstants.PATH_SEP,
						WebConstants.PATH_SIGNUP,
						WebConstants.PATH_SIGNIN,
						WebConstants.PATH_SIGNOUT,
						WebConstants.PATH_ABOUT,
						"/pages/**"
				).permitAll()
				//The rest of the our application is protected.
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated() // 所有其他的URL都需要用户进行验证
				//.anyRequest().permitAll()
				//.accessDecisionManager(ACCESS_DECISION_MGR)
				.and()
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
				.usernameParameter("u")
				.passwordParameter("p")
				.permitAll()
				.and()
			//Adds the SocialAuthenticationFilter to Spring Security's filter chain.
			//	.apply(new SpringSocialConfigurer())
			//	.and()
			.setSharedObject(ApplicationContext.class, context);
	}
	
	@Override
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsServiceBean())
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return myUserDetailsService;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
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
