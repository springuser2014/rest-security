package net.zzh.sec.persistence.setup;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import com.google.common.base.Preconditions;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private final Logger logger = LoggerFactory.getLogger(MyApplicationContextInitializer.class);

    private static final String ENV_TARGET = "envTarget";
    private static final String PERSISTENCE_TARGET = "persistenceTarget";

    //

    /**
     * Sets the active profile.
     */
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String envTarget = null;
        try {
            envTarget = getEnvTarget(environment);
            environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:env-" + envTarget + ".properties"));

            final String activeProfiles = environment.getProperty("spring.profiles.active");
            logger.info("The active profiles are: {}", activeProfiles);

            environment.setActiveProfiles(activeProfiles.split(","));
        } catch (final IOException ioEx) {
            if (envTarget != null) {
                logger.warn("Didn't find env-{}.properties in classpath so not loading it in the AppContextInitialized", envTarget);
            }
        }

        final String persistenceTarget = environment.getProperty(PERSISTENCE_TARGET);
        if (persistenceTarget == null) {
            logger.info("Didn't find a value for variable: {}", PERSISTENCE_TARGET);
        } else {
            logger.trace("value for variable: {} is: {}", PERSISTENCE_TARGET, persistenceTarget);
        }
    }

    /**
     * @param environment
     * @return The env target variable.
     */
    private String getEnvTarget(final ConfigurableEnvironment environment) {
        String envTarget;

        final String targetOverride = getTargetFromOverride();
        if (targetOverride == null) {
            envTarget = environment.getProperty(ENV_TARGET);
        } else {
            envTarget = targetOverride;
        }
        if (envTarget == null) {
            logger.warn("Didn't find a value for {} in the current Environment!", ENV_TARGET);
        }

        if (envTarget == null) {
            logger.info("Didn't find a value for {} in the current Environment!, using the default `dev`", ENV_TARGET);
            envTarget = "dev";
        }

        return Preconditions.checkNotNull(envTarget);
    }

    /**
     * This enables overriding the env-${envTarget}.properties location, which is by default resolved internally from the classpath. The entire purpose of overriding this specific properties file is to be able to control
     * the active Spring profile without defining system wide variables on the OS that runs staging
     */
    private final String getTargetFromOverride() {
        try {
            final ResourcePropertySource overrideProperties = new ResourcePropertySource("file:///opt/override/overrides.properties");
            return (String) overrideProperties.getProperty(ENV_TARGET);
        } catch (final IOException e) {
            logger.trace("The file overrides.properties is not accessible. No property overridden by external properties");
        }

        return null;
    }

}
