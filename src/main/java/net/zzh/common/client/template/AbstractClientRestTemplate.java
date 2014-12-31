package net.zzh.common.client.template;

import org.apache.commons.lang3.tuple.Pair;
import net.zzh.common.client.web.HeaderUtil;
import net.zzh.common.persistence.model.IEntity;
import net.zzh.common.web.WebConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Preconditions;

public abstract class AbstractClientRestTemplate<T extends IEntity> extends AbstractReadOnlyClientRestTemplate<T> {

    public AbstractClientRestTemplate(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // create

    public final T create(final T resource, final Pair<String, String> credentials) {
        final String locationOfCreatedResource = createAsUri(resource, credentials);

        return findOneByUri(locationOfCreatedResource, credentials);
    }

    public final T create(final T resource) {
        final String locationOfCreatedResource = createAsUri(resource, null);

        return findOneByUri(locationOfCreatedResource, null);
    }

    public final String createAsUri(final T resource, final Pair<String, String> credentials) {
        if (credentials != null) {
            auth.givenAuthenticated(restTemplate, credentials.getLeft(), credentials.getRight());
        } else {
            givenAuthenticated();
        }
        final ResponseEntity<Void> responseEntity = restTemplate.exchange(getUri(), HttpMethod.POST, new HttpEntity<T>(resource, writeHeaders()), Void.class);

        final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
        Preconditions.checkNotNull(locationOfCreatedResource);

        return locationOfCreatedResource;
    }

    // update

    public final void update(final int id, final T resource) {
        givenAuthenticated();
        final ResponseEntity<T> responseEntity = restTemplate.exchange(getUri() + "/" + id, HttpMethod.PUT, new HttpEntity<T>(resource, writeHeaders()), clazz);
        Preconditions.checkState(responseEntity.getStatusCode().value() == 200);
    }

    // delete

    public final void delete(final long id) {
        // final ResponseEntity<Object> deleteResourceResponse = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.DELETE, new HttpEntity<T>(writeHeaders()), null);
        final ResponseEntity<Void> deleteResourceResponse = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.DELETE, new HttpEntity<Void>(writeHeaders()), Void.class);

        Preconditions.checkState(deleteResourceResponse.getStatusCode().value() == 204);
    }

    public final void deleteAll() {
        throw new UnsupportedOperationException();
    }

    // template method

    @Override
    public abstract Pair<String, String> getDefaultCredentials();

    /**
     * - this is a hook that executes before read operations, in order to allow custom security work to happen for read operations; similar to: AbstractRestTemplate.findRequest
     */
    @Override
    protected void beforeReadOperation() {
        //
    }

    /**
     * - note: hook to be able to customize the find headers if needed
     */
    @Override
    protected HttpHeaders findHeaders() {
        return HeaderUtil.createAcceptHeaders(marshaller);
    }

    /**
     * - note: hook to be able to customize the write headers if needed
     */
    protected HttpHeaders writeHeaders() {
        return HeaderUtil.createContentTypeHeaders(marshaller);
    }

}
