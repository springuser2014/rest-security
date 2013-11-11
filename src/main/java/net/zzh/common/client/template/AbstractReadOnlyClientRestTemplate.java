package net.zzh.common.client.template;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.client.marshall.IMarshaller;
import net.zzh.common.client.security.ClientAuthenticator;
import net.zzh.common.client.web.HeaderUtil;
import net.zzh.common.persistence.model.IEntity;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.search.SearchUriBuilder;
import net.zzh.common.util.QueryConstants;
import net.zzh.common.web.WebConstants;
import net.zzh.common.web.util.UriUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractReadOnlyClientRestTemplate<T extends IEntity> implements IRawClientTemplate<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<T> clazz;

    @Autowired
    protected IMarshaller marshaller;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ClientAuthenticator auth;

    public AbstractReadOnlyClientRestTemplate(final Class<T> clazzToSet) {
        super();

        clazz = clazzToSet;
    }

    // find - one

    public final T findOne(final long id, final Pair<String, String> credentials) {
        if (credentials != null) {
            auth.givenAuthenticated(restTemplate, credentials.getLeft(), credentials.getRight());
        } else {
            givenAuthenticated();
        }

        try {
            final ResponseEntity<T> response = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.GET, findRequestEntity(), clazz);
            return response.getBody();
        } catch (final HttpClientErrorException clientEx) {
            return null;
        }
    }

    public final T findOne(final long id) {
        try {
            final ResponseEntity<T> response = restTemplate.exchange(getUri() + WebConstants.PATH_SEP + id, HttpMethod.GET, findRequestEntity(), clazz);
            return response.getBody();
        } catch (final HttpClientErrorException clientEx) {
            return null;
        }
    }

    public final T findOneByUri(final String uri, final Pair<String, String> credentials) {
        final ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), clazz);
        return response.getBody();
    }

    // find - all

    public final List<T> findAll() {
        beforeReadOperation();
        final ResponseEntity<String> findAllResponse = restTemplate.exchange(getUri(), HttpMethod.GET, findRequestEntity(), String.class);
        final String body = findAllResponse.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return marshaller.decodeList(body, clazz);
    }

    public final List<T> findAllSorted(final String sortBy, final String sortOrder) {
        beforeReadOperation();
        final String uri = getUri() + QueryConstants.Q_SORT_BY + sortBy + QueryConstants.S_ORDER + sortOrder;
        final ResponseEntity<String> findAllResponse = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), String.class);
        final String body = findAllResponse.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return marshaller.decodeList(body, clazz);
    }

    public final List<T> findAllPaginated(final int page, final int size) {
        beforeReadOperation();
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(QueryConstants.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        final ResponseEntity<List> findAllResponse = restTemplate.exchange(uri.toString(), HttpMethod.GET, findRequestEntity(), List.class);
        final List<T> body = findAllResponse.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return body;
    }

    public final List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final ResponseEntity<String> allPaginatedAndSortedAsResponse = findAllPaginatedAndSortedAsResponse(page, size, sortBy, sortOrder);
        final String bodyAsString = allPaginatedAndSortedAsResponse.getBody();
        if (bodyAsString == null) {
            return Lists.newArrayList();
        }
        return marshaller.decodeList(bodyAsString, clazz);
    }

    public final List<T> findAllByUri(final String uri, final Pair<String, String> credentials) {
        final ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), List.class);
        final List<T> body = response.getBody();
        if (body == null) {
            return Lists.newArrayList();
        }
        return body;
    }

    // as response

    final ResponseEntity<String> findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder) {
        beforeReadOperation();
        final StringBuilder uri = new StringBuilder(getUri());
        uri.append(QueryConstants.QUESTIONMARK);
        uri.append("page=");
        uri.append(page);
        uri.append(QueryConstants.SEPARATOR_AMPER);
        uri.append("size=");
        uri.append(size);
        Preconditions.checkState(!(sortBy == null && sortOrder != null));
        if (sortBy != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_BY + "=");
            uri.append(sortBy);
        }
        if (sortOrder != null) {
            uri.append(QueryConstants.SEPARATOR_AMPER);
            uri.append(QueryConstants.SORT_ORDER + "=");
            uri.append(sortOrder);
        }

        return restTemplate.exchange(uri.toString(), HttpMethod.GET, findRequestEntity(), String.class);
    }

    protected final ResponseEntity<List> findAllAsResponse(final String uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), List.class);
    }

    // search

    public final List<T> searchAll(final Triple<String, ClientOperation, String>... constraints) {
        beforeReadOperation();
        final SearchUriBuilder builder = new SearchUriBuilder();
        for (final Triple<String, ClientOperation, String> constraint : constraints) {
            builder.consume(constraint);
        }

        final URI uri = UriUtil.createSearchUri(getUri() + QueryConstants.QUERY_PREFIX + "{qu}", builder.build());
        final ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, findRequestEntity(), String.class);
        Preconditions.checkState(response.getStatusCode().value() == 200);

        return marshaller.decodeList(response.getBody(), clazz);
    }

    public final T searchOne(final Triple<String, ClientOperation, String>... constraints) {
        final List<T> all = searchAll(constraints);
        if (all.isEmpty()) {
            return null;
        }
        Preconditions.checkState(all.size() <= 1);
        return all.get(0);
    }

    // count

    public final long count() {
        final ResponseEntity<Long> countResourceResponse = restTemplate.exchange(getUri() + "/count", HttpMethod.GET, null, Long.class);
        Preconditions.checkState(countResourceResponse.getStatusCode().value() == 200);
        return countResourceResponse.getBody();
    }

    // util

    protected final HttpEntity<Void> findRequestEntity() {
        return new HttpEntity<Void>(findHeaders());
    }

    // template method

    public abstract Pair<String, String> getDefaultCredentials();

    public final void givenAuthenticated() {
        final Pair<String, String> defaultCredentials = getDefaultCredentials();
        auth.givenAuthenticated(restTemplate, defaultCredentials.getLeft(), defaultCredentials.getRight());
    }

    /**
     * - this is a hook that executes before read operations, in order to allow custom security work to happen for read operations; similar to: AbstractRestTemplate.findRequest
     */
    protected void beforeReadOperation() {
        //
    }

    /**
     * - note: hook to be able to customize the find headers if needed
     */
    protected HttpHeaders findHeaders() {
        return HeaderUtil.createAcceptHeaders(marshaller);
    }

}
