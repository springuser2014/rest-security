package net.zzh.sec.client.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.zzh.common.client.template.AbstractNamedClientRestTemplate;
import net.zzh.sec.client.SecBusinessPaths;
import net.zzh.sec.model.dto.UserProfile;
import net.zzh.sec.util.SecurityConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public class UserClientRestTemplate extends AbstractNamedClientRestTemplate<UserProfile> {

    @Autowired
    private SecBusinessPaths paths;

    public UserClientRestTemplate() {
        super(UserProfile.class);
    }

    // operations

    // template method

    @Override
    public final String getUri() {
        return paths.getUserUri();
    }

    @Override
    public final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS);
    }

    @Override
    protected void beforeReadOperation() {
        super.beforeReadOperation();
    }

}
