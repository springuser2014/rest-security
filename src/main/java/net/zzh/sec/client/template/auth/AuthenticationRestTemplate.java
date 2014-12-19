package net.zzh.sec.client.template.auth;

import net.zzh.common.client.marshall.IMarshaller;
import net.zzh.common.client.web.HeaderUtil;
import net.zzh.sec.client.SecBusinessPaths;
import net.zzh.sec.model.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("client")
public class AuthenticationRestTemplate {

    @Autowired
    IMarshaller marshaller;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SecBusinessPaths paths;

    public AuthenticationRestTemplate() {
        super();
    }

    // API

    public final ResponseEntity<UserProfile> authenticate(final String username, final String password) {
        return restTemplate.exchange(getUri(), HttpMethod.GET, new HttpEntity<UserProfile>(createHeaders(username, password)), UserProfile.class);
    }

    // util

    final String getUri() {
        return paths.getAuthenticationUri();
    }

    final HttpHeaders createHeaders(final String username, final String password) {
        return HeaderUtil.createAcceptAndBasicAuthHeaders(marshaller, username, password);
    }

}
