package net.zzh.sec.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "net.zzh.sec.common" })
public class SecCommonApiConfig {

    public SecCommonApiConfig() {
        super();
    }

}
