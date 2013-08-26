package net.zzh.common.client.template;

import net.zzh.common.persistence.model.INameableEntity;
import net.zzh.common.persistence.service.INameSupport;

public interface IClientTemplate<T extends INameableEntity> extends IRawClientTemplate<T>, INameSupport<T> {

    //

}
