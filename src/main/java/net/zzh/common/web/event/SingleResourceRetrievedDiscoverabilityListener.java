package net.zzh.common.web.event;

import static net.zzh.common.web.WebConstants.PATH_SEP;

import javax.servlet.http.HttpServletResponse;

import net.zzh.common.event.SingleResourceRetrievedEvent;
import net.zzh.common.util.LinkUtil;
import net.zzh.common.web.IUriMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

@SuppressWarnings("rawtypes")
@Component
final class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    public SingleResourceRetrievedDiscoverabilityListener() {
        super();
    }

    //

    public final void onApplicationEvent(final SingleResourceRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);

        discoverGetAllURI(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    @SuppressWarnings("unchecked")
    final void discoverGetAllURI(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String uriForResourceCreation = uriBuilder.path(PATH_SEP + uriMapper.getUriBase(clazz)).build().encode().toUriString();

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }

}
