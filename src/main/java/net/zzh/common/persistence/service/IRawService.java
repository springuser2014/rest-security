package net.zzh.common.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.IOperations;
import net.zzh.common.persistence.model.IEntity;
import net.zzh.common.search.ClientOperation;
import org.springframework.data.domain.Page;

public interface IRawService<T extends IEntity> extends IOperations<T> {

    // search

    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);

}
