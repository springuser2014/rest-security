package net.zzh.common.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import net.zzh.common.persistence.ServicePreconditions;
import net.zzh.common.persistence.event.AfterEntitiesDeletedEvent;
import net.zzh.common.persistence.event.AfterEntityCreateEvent;
import net.zzh.common.persistence.event.AfterEntityDeleteEvent;
import net.zzh.common.persistence.event.AfterEntityUpdateEvent;
import net.zzh.common.persistence.event.BeforeEntityCreateEvent;
import net.zzh.common.persistence.event.BeforeEntityDeleteEvent;
import net.zzh.common.persistence.event.BeforeEntityUpdateEvent;
import net.zzh.common.persistence.model.IEntity;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.util.SearchCommonUtil;
import net.zzh.common.web.exception.BadRequestException;
import net.zzh.common.web.exception.ConflictException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Transactional
public abstract class AbstractRawService<T extends IEntity> implements IRawService<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Class<T> clazz;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractRawService(final Class<T> clazzToSet) {
        super();

        clazz = clazzToSet;
    }

    // API

    // search

    @SuppressWarnings("unchecked")
    public List<T> searchAll(final String queryString) {
        Preconditions.checkNotNull(queryString);
        List<Triple<String, ClientOperation, String>> parsedQuery = null;
        try {
            parsedQuery = SearchCommonUtil.parseQueryString(queryString);
        } catch (final IllegalStateException illState) {
            logger.error("IllegalStateException on find operation");
            logger.warn("IllegalStateException on find operation", illState);
            throw new BadRequestException(illState);
        }

        final List<T> results = searchAll(parsedQuery.toArray(new ImmutableTriple[parsedQuery.size()]));
        return results;
    }

    @SuppressWarnings({ "unchecked" })
    public List<T> searchPaginated(final String queryString, final int page, final int size) {
        List<Triple<String, ClientOperation, String>> parsedQuery = null;
        try {
            parsedQuery = SearchCommonUtil.parseQueryString(queryString);
        } catch (final IllegalStateException illState) {
            logger.error("IllegalStateException on find operation");
            logger.warn("IllegalStateException on find operation", illState);
            throw new ConflictException(illState);
        }

        final Page<T> resultPage = searchPaginated(page, size, parsedQuery.toArray(new ImmutableTriple[parsedQuery.size()]));
        return Lists.newArrayList(resultPage.getContent());
    }

    @SuppressWarnings("null")
    public List<T> searchAll(final Triple<String, ClientOperation, String>... constraints) {
        Preconditions.checkState(constraints != null);
        Preconditions.checkState(constraints.length > 0);
        final Specification<T> firstSpec = resolveConstraint(constraints[0]);
        Specifications<T> specifications = Specifications.where(firstSpec);
        for (int i = 1; i < constraints.length; i++) {
            specifications = specifications.and(resolveConstraint(constraints[i]));
        }
        if (firstSpec == null) {
            return Lists.newArrayList();
        }

        return getSpecificationExecutor().findAll(specifications);
    }

    @SuppressWarnings("null")
    public T searchOne(final Triple<String, ClientOperation, String>... constraints) {
        Preconditions.checkState(constraints != null);
        Preconditions.checkState(constraints.length > 0);
        final Specification<T> firstSpec = resolveConstraint(constraints[0]);
        Specifications<T> specifications = Specifications.where(firstSpec);
        for (int i = 1; i < constraints.length; i++) {
            specifications = specifications.and(resolveConstraint(constraints[i]));
        }
        if (firstSpec == null) {
            return null;
        }

        return getSpecificationExecutor().findOne(specifications);
    }

    public Page<T> searchPaginated(final int page, final int size, final Triple<String, ClientOperation, String>... constraints) {
        final Specification<T> firstSpec = resolveConstraint(constraints[0]);
        Preconditions.checkState(firstSpec != null);
        Specifications<T> specifications = Specifications.where(firstSpec);
        for (int i = 1; i < constraints.length; i++) {
            specifications = specifications.and(resolveConstraint(constraints[i]));
        }

        return getSpecificationExecutor().findAll(specifications, new PageRequest(page, size, null));
    }

    // exist

    @Transactional(readOnly = true)
    public boolean exists(final long id) {
        return getDao().exists(id);
    };
    
    // find - one

    @Transactional(readOnly = true)
    public T findOne(final long id) {
        return getDao().findOne(id);
    }

    // find - all

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        return getDao().findAll(new PageRequest(page, size, sortInfo));
    }

    @Transactional(readOnly = true)
    public List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        final List<T> content = getDao().findAll(new PageRequest(page, size, sortInfo)).getContent();
        if (content == null) {
            return Lists.newArrayList();
        }
        return content;
    }

    @Transactional(readOnly = true)
    public List<T> findAllPaginated(final int page, final int size) {
        final List<T> content = getDao().findAll(new PageRequest(page, size, null)).getContent();
        if (content == null) {
            return Lists.newArrayList();
        }
        return content;
    }

    @Transactional(readOnly = true)
    public List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        return Lists.newArrayList(getDao().findAll(sortInfo));
    }

    // save/create/persist

    public T create(final T entity) {
        Preconditions.checkNotNull(entity);

        eventPublisher.publishEvent(new BeforeEntityCreateEvent<T>(this, clazz, entity));
        final T persistedEntity = getDao().save(entity);
        eventPublisher.publishEvent(new AfterEntityCreateEvent<T>(this, clazz, persistedEntity));

        return persistedEntity;
    }

    // update/merge

    public void update(final T entity) {
        Preconditions.checkNotNull(entity);

        eventPublisher.publishEvent(new BeforeEntityUpdateEvent<T>(this, clazz, entity));
        getDao().save(entity);
        eventPublisher.publishEvent(new AfterEntityUpdateEvent<T>(this, clazz, entity));
    }

    // delete

    public void deleteAll() {
        getDao().deleteAll();
        eventPublisher.publishEvent(new AfterEntitiesDeletedEvent<T>(this, clazz));
    }

    public void delete(final long id) {
        final T entity = getDao().findOne(id);
        ServicePreconditions.checkEntityExists(entity);

        eventPublisher.publishEvent(new BeforeEntityDeleteEvent<T>(this, clazz, entity));
        getDao().delete(entity);
        eventPublisher.publishEvent(new AfterEntityDeleteEvent<T>(this, clazz, entity));
    }

    // count

    public long count() {
        return getDao().count();
    }

    // template method
    protected abstract PagingAndSortingRepository<T, Long> getDao();

    protected abstract JpaSpecificationExecutor<T> getSpecificationExecutor();

    @SuppressWarnings({ "static-method", "unused" })
    public Specification<T> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        throw new UnsupportedOperationException();
    }

    // template

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

}
