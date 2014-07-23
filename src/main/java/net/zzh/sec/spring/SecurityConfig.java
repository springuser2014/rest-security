package net.zzh.sec.spring;

import java.util.Locale;

import net.zzh.common.web.WebConstants;
import net.zzh.sec.security.CustomUserDetailsService;
import net.zzh.sec.security.CustomThemeResolver;
import net.zzh.sec.security.SecurityLoginFailureHandler;
import net.zzh.sec.security.SecurityLoginSuccessHandler;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
@ComponentScan("net.zzh.sec.security")
@EnableWebSecurity
@EnableTransactionManagement(proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private SecurityLoginSuccessHandler securityLoginSuccessHandler;
	
	@Autowired
	private SecurityLoginFailureHandler securityLoginFailureHandler;
	
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
				.loginProcessingUrl(WebConstants.PATH_SIGNIN)
				.successHandler(securityLoginSuccessHandler)
				.failureHandler(securityLoginFailureHandler)
				.usernameParameter("u")
				.passwordParameter("p")
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
			.csrf().disable()
			.setSharedObject(ApplicationContext.class, context);
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
		CustomThemeResolver customThemeResolver = new CustomThemeResolver();
		customThemeResolver.setDefaultThemeName(WebConstants.ORIGINAL_DEFAULT_THEME_NAME);
		return customThemeResolver;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return cookieLocaleResolver;
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
/*
	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}
	*/
}
