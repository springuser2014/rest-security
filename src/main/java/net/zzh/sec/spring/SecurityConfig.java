package net.zzh.sec.spring;

import net.zzh.common.web.WebConstants;
import net.zzh.sec.security.MyThemeResolver;
import net.zzh.sec.security.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
@ComponentScan("net.zzh.sec.security")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	public SecurityConfig() {
		super();
	}

	// API
	
	/**
	 * authentication manager
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService);
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
			.authorizeRequests()
				.antMatchers(
						WebConstants.PATH_SIGNUP,
						WebConstants.PATH_SIGNIN,
						WebConstants.PATH_SIGNOUT,
						WebConstants.PATH_ABOUT
				).permitAll()
				//The rest of the our application is protected.
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage(WebConstants.PATH_SIGNIN)
				.permitAll()
				.and()
			//Configures the logout function
			.logout()
				.deleteCookies("JSESSIONID")
				.logoutUrl(WebConstants.PATH_SIGNOUT)
				.logoutSuccessUrl(WebConstants.PATH_SIGNIN)
				.and()
			.httpBasic()
				.and()
			.rememberMe()
				.and()
			.setSharedObject(ApplicationContext.class, context);
	}
	
	@Bean
	public LocaleResolver localeResolver() {

		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("zh_CN"));
		return cookieLocaleResolver;
	}

	/**
	 * theme source
	 * @return
	 */
	@Bean
	public ThemeSource themeSource() {
		
		ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
		themeSource.setBasenamePrefix(WebConstants.THEME_PREFIX);
		return themeSource;
	}

	/**
	 * custom theme
	 * @return
	 */
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
