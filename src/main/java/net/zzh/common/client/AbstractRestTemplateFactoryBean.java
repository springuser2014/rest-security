package net.zzh.common.client;

import org.apache.http.HttpHost;
import net.zzh.common.security.DigestHttpComponentsClientHttpRequestFactory;
import net.zzh.common.security.PreemptiveAuthHttpRequestFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractRestTemplateFactoryBean implements FactoryBean<RestTemplate>, InitializingBean {
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    public AbstractRestTemplateFactoryBean() {
        super();
    }

    // API

    public RestTemplate getObject() {
        return restTemplate;
    }

    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() {
        final HttpComponentsClientHttpRequestFactory requestFactory;
        final int timeout = env.getProperty("http.req.timeout", Integer.class);
        if (env.getProperty("sec.auth.basic", Boolean.class)) {
            final int port = env.getProperty("http.port", Integer.class);
            final String host = env.getProperty("http.host");
            requestFactory = new PreemptiveAuthHttpRequestFactory(host, port, HttpHost.DEFAULT_SCHEME_NAME);
            requestFactory.setReadTimeout(timeout);
        } else {
            requestFactory = new DigestHttpComponentsClientHttpRequestFactory() {
                {
                    setReadTimeout(timeout);
                }
            };
        }
        restTemplate = new RestTemplate(requestFactory);

        restTemplate.getMessageConverters().remove(5); // removing the Jaxb2RootElementHttpMessageConverter
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(marshallingHttpMessageConverter());
    }

    //

    protected final MarshallingHttpMessageConverter marshallingHttpMessageConverter() {
        final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
        marshallingHttpMessageConverter.setMarshaller(xstreamMarshaller());
        marshallingHttpMessageConverter.setUnmarshaller(xstreamMarshaller());

        return marshallingHttpMessageConverter;
    }

    protected abstract XStreamMarshaller xstreamMarshaller();

}
