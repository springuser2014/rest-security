package net.zzh.common.client.template;

import static net.zzh.common.search.ClientOperation.EQ;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import net.zzh.common.persistence.model.INameableEntity;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.util.SearchField;

@SuppressWarnings({ "unchecked" })
public abstract class AbstractNamedClientRestTemplate<T extends INameableEntity> extends AbstractClientRestTemplate<T> implements IClientTemplate<T> {

    public AbstractNamedClientRestTemplate(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // find one - by attributes

    public T findByName(final String name) {
        beforeReadOperation();
        return searchOne(new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, name));
    }

    // util

    protected void auth(final Pair<String, String> credentials) {
        if (credentials != null) {
            auth.givenAuthenticated(restTemplate, credentials.getLeft(), credentials.getRight());
        } else {
            givenAuthenticated();
        }
    }

}
