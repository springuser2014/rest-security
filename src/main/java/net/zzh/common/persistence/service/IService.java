package net.zzh.common.persistence.service;

import org.springframework.data.domain.Page;

import net.zzh.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, INameSupport<T> {

    //

}
