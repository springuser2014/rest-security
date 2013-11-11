package net.zzh.common.client.template;

import org.apache.commons.lang3.tuple.Pair;
import net.zzh.common.IOperations;
import net.zzh.common.persistence.model.IEntity;

public interface IRawClientTemplate<T extends IEntity> extends IOperations<T>, ITemplateWithUri<T> {

    //

    String getUri();

    // create

    T create(final T resource, final Pair<String, String> credentials);

    T findOne(final long id, final Pair<String, String> credentials);

}
