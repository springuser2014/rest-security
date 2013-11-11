package net.zzh.common.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "net.zzh.common.client" })
public class CommonClientConfig {

    public CommonClientConfig() {
        super();
    }

}
