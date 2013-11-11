package net.zzh.common.client.template;

import net.zzh.common.spring.CommonSpringProfileUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Profile(CommonSpringProfileUtil.CLIENT)
public final class CommonPaths implements InitializingBean {

    @Autowired
    private Environment env;

    private String protocol;
    private String host;
    private String port;

    public CommonPaths() {
        super();
    }

    // API

    public final String getServerRoot() {
        return protocol + "://" + host + ":" + port;
    }

    public void afterPropertiesSet() {
        protocol = env.getProperty("http.protocol");
        host = env.getProperty("http.host");
        port = env.getProperty("http.port");
    }

}
