package net.zzh.sec.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@ComponentScan({ "net.zzh.common.web", "net.zzh.sec.web", "net.zzh.ui.web" })
@EnableWebMvc // Add @EnableWebMVC, this is the same as <mvc:annotation-driven/>
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private FormattingConversionService mvcConversionService;
	
	// API
	
	/**
	 * Set default servlet handler, this is the same as <mvc:default-servlet-handler/>
	 * This tag allows for mapping the DispatcherServlet to "/"
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * Declare our static resources.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		//registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/resources/fonts/");
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
		registry.addResourceHandler("/ui/**").addResourceLocations("/resources/ui/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
		//registry.addInterceptor(new ThemeChangeInterceptor()).addPathPatterns("/**").excludePathPatterns("/mobile/**");
		//registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
	}

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/html5.html");
		
		registry.addViewController("/session-timeout").setViewName("/");
		
		registry.addViewController("/errors/404").setViewName("errors/404");
		
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// formatters / converters
	}
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		//viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/app/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	/**
	 * Setup a simple strategy: 1. Only path extension is taken into account,
	 * Accept headers are ignored. 2. Return HTML by default when not sure.
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(true);
		Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		mediaTypes.put("xml", MediaType.APPLICATION_XML);
		mediaTypes.put("xml2", MediaType.TEXT_XML);
		mediaTypes.put("xhtml", MediaType.APPLICATION_XHTML_XML);
		mediaTypes.put("html", MediaType.TEXT_HTML);
		configurer.mediaTypes(mediaTypes);
		configurer.defaultContentType(MediaType.TEXT_HTML);
	}

	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}
	
	@Bean
	public ThemeChangeInterceptor themeChangeInterceptor() {
		ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
		themeChangeInterceptor.setParamName("theme");
		return themeChangeInterceptor;
	}
	
	@Bean
	public DomainClassConverter<?> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService);
	}
	
	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new MappingJackson2HttpMessageConverter());
	}
	
/*
	// beans

	public XStreamMarshaller xstreamMarshaller() {
		final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		xStreamMarshaller.setAutodetectAnnotations(true);
		xStreamMarshaller.setAnnotatedClasses(new Class[] { Principal.class, User.class, Role.class, Privilege.class });
	   // xStreamMarshaller.getXStream().addDefaultImplementation(java.sql.Timestamp.class, java.util.Date.class);

		return xStreamMarshaller;
	}

	public MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
		final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
		final XStreamMarshaller xstreamMarshaller = xstreamMarshaller();
		marshallingHttpMessageConverter.setMarshaller(xstreamMarshaller);
		marshallingHttpMessageConverter.setUnmarshaller(xstreamMarshaller);

		return marshallingHttpMessageConverter;
	}

	// template

	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> messageConverters) {
		messageConverters.add(marshallingHttpMessageConverter());

		final ClassLoader classLoader = getClass().getClassLoader();
		if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader)) {
			messageConverters.add(new MappingJackson2HttpMessageConverter());
		} else if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", classLoader)) {
			messageConverters.add(new MappingJacksonHttpMessageConverter());
		}

		super.configureMessageConverters(messageConverters);
	}*/
}
