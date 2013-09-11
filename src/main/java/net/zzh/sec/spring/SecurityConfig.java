package net.zzh.sec.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("net.zzh.sec.security")
@ImportResource({ "classpath*:*secSecurityConfig.xml" })
public class SecurityConfig {

    public SecurityConfig() {
        super();
    }

}
