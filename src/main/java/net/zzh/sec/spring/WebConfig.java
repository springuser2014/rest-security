package net.zzh.sec.spring;

import java.util.List;

import net.zzh.sec.model.Principal;
import net.zzh.sec.model.Privilege;
import net.zzh.sec.model.Role;
import net.zzh.sec.model.dto.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan({ "net.zzh.common.web", "net.zzh.sec.web", "net.zzh.ui.web" })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        super();
    }

    // API
/*
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);

        registry.addViewController("/sample.html");
    }
*//*
    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();

        //bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/app/");
        bean.setSuffix(".jsp");

        return bean;
    }*/
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
