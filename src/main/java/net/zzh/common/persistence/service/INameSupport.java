package net.zzh.common.persistence.service;

import net.zzh.common.persistence.model.INameableEntity;

public interface INameSupport<T extends INameableEntity> {

    T findByName(final String name);

}
