package net.zzh.common.client.template;

import org.apache.commons.lang3.tuple.Pair;
import net.zzh.common.persistence.model.IEntity;

public interface ITemplateWithUri<T extends IEntity> extends IReadOnlyTemplateWithUri<T> {

    // create

    String createAsUri(final T resource, final Pair<String, String> credentials);

}
