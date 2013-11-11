package net.zzh.common.web.event;

import static net.zzh.common.web.WebConstants.PATH_SEP;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import net.zzh.common.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

@Component
@SuppressWarnings("rawtypes")
public class ResourceCreatedDiscoverabilityListener implements ApplicationListener<ResourceCreatedEvent> {

    public ResourceCreatedDiscoverabilityListener() {
        super();
    }

    //

    public final void onApplicationEvent(final ResourceCreatedEvent ev) {
        Preconditions.checkNotNull(ev);

        final String idOfNewResource = ev.getIdOfNewResource();
        addLinkHeaderOnEntityCreation(ev.getUriBuilder(), ev.getResponse(), idOfNewResource, ev.getClazz());
    }

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    final void addLinkHeaderOnEntityCreation(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final String idOfNewEntity, final Class clazz) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase();
        final String locationValue = uriBuilder.path(PATH_SEP + resourceName + "s/{id}").build().expand(idOfNewEntity).encode().toUriString();

        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }

}
