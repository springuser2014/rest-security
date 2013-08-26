package net.zzh.sec.persistence.setup;

import net.zzh.common.event.BeforeSetupEvent;
import net.zzh.sec.web.controller.AuthenticationController;
import net.zzh.sec.web.controller.PrivilegeController;
import net.zzh.sec.web.controller.RoleController;
import net.zzh.sec.web.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
@Profile("dev")
public final class SpringVerificationsBeforeSetupListener implements ApplicationListener<BeforeSetupEvent> {
    // private final Logger logger = LoggerFactory.getLogger(SpringVerificationsBeforeSetupListener.class);

    @Autowired
    ApplicationContext context;

    public SpringVerificationsBeforeSetupListener() {
        super();
    }

    // API

    public final void onApplicationEvent(final BeforeSetupEvent event) {
        Preconditions.checkNotNull(context.getBean(PrivilegeController.class));
        Preconditions.checkNotNull(context.getBean(RoleController.class));
        Preconditions.checkNotNull(context.getBean(UserController.class));
        Preconditions.checkNotNull(context.getBean(AuthenticationController.class));
    }

}
