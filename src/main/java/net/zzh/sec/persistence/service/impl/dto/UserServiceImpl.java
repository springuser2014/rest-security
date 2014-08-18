package net.zzh.sec.persistence.service.impl.dto;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.web.RestPreconditions;
import net.zzh.sec.model.dto.UserToProfileFunction;
import net.zzh.sec.model.dto.Profile;
import net.zzh.sec.persistence.service.IUserService;
import net.zzh.sec.persistence.service.dto.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

//@Service
//@Transactional
public class UserServiceImpl implements IUsersService {

    @Autowired
    private IUserService principalService;

    public UserServiceImpl() {
        super();
    }

    // API

    // search

    @Transactional(readOnly = true)
    public List<Profile> searchAll(final Triple<String, ClientOperation, String>... constraints) {
        final List<Principal> principalsResultedFromSearch = principalService.searchAll(constraints);
        final List<Profile> usersResultedFromSearch = Lists.transform(principalsResultedFromSearch, new UserToProfileFunction());

        return usersResultedFromSearch;
    }

    @Transactional(readOnly = true)
    public Profile searchOne(final Triple<String, ClientOperation, String>... constraints) {
        final Principal principalResultedFromSearch = principalService.searchOne(constraints);
        final Profile userResultedFromSearch = new UserToProfileFunction().apply(principalResultedFromSearch);

        return userResultedFromSearch;
    }

    @Transactional(readOnly = true)
    public Page<Profile> searchPaginated(final int page, final int size, final Triple<String, ClientOperation, String>... constraints) {
        final Page<Principal> principalsPaginated = principalService.searchPaginated(page, size, constraints);

        final List<Profile> usersPaginated = Lists.transform(principalsPaginated.getContent(), new UserToProfileFunction());

        return new PageImpl<Profile>(usersPaginated, new PageRequest(page, size, null), principalsPaginated.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<Profile> searchAll(final String queryString) {
        final List<Principal> principals = principalService.searchAll(queryString);
        final List<Profile> users = Lists.transform(principals, new UserToProfileFunction());
        return users;
    }

    @Transactional(readOnly = true)
    public List<Profile> searchPaginated(final String queryString, final int page, final int size) {
        final List<Principal> principals = principalService.searchPaginated(queryString, page, size);
        final List<Profile> users = Lists.transform(principals, new UserToProfileFunction());
        return users;
    }

    // find - one

    @Transactional(readOnly = true)
    public Profile findByName(final String name) {
        final Principal principal = principalService.findByName(name);
        return new Profile(principal);
    }

    @Transactional(readOnly = true)
    public Profile findOne(final long id) {
        final Principal principal = principalService.findOne(id);
        if (principal == null) {
            return null;
        }
        return new Profile(principal);
    }

    // find - many

    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        final List<Principal> allPrincipalEntities = principalService.findAll();
        final List<Profile> allUsers = Lists.transform(allPrincipalEntities, new UserToProfileFunction());

        return Lists.newArrayList(allUsers);
    }

    @Transactional(readOnly = true)
    public List<Profile> findAllSorted(final String sortBy, final String sortOrder) {
        final List<Principal> allPrincipalEntitiesSortedAndOrdered = principalService.findAllSorted(sortBy, sortOrder);
        final List<Profile> allUsers = Lists.transform(allPrincipalEntitiesSortedAndOrdered, new UserToProfileFunction());

        return allUsers;
    }

    @Transactional(readOnly = true)
    public List<Profile> findAllPaginated(final int page, final int size) {
        final List<Principal> principalsPaginated = principalService.findAllPaginated(page, size);
        return Lists.transform(principalsPaginated, new UserToProfileFunction());
    }

    @Transactional(readOnly = true)
    public Page<Profile> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        final Page<Principal> principalsPaginatedAndSorted = principalService.findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder);

        final List<Profile> usersPaginatedAndSorted = Lists.transform(principalsPaginatedAndSorted.getContent(), new UserToProfileFunction());

        return new PageImpl<Profile>(usersPaginatedAndSorted, new PageRequest(page, size, constructSort(sortBy, sortOrder)), principalsPaginatedAndSorted.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<Profile> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        return findAllPaginatedAndSortedRaw(page, size, sortBy, sortOrder).getContent();
    }

    // create

    public Profile create(final Profile entity) {
        final Principal newPrincipalEntity = new Principal(entity.getName(), entity.getPassword(), entity.getRoles());
        principalService.create(newPrincipalEntity);
        entity.setId(newPrincipalEntity.getId());
        return entity;
    }

    // update

    public void update(final Profile entity) {
        final Principal principalToUpdate = RestPreconditions.checkNotNull(principalService.findOne(entity.getId()));

        principalToUpdate.setName(entity.getName());
        principalToUpdate.setRoles(entity.getRoles());

        principalService.update(principalToUpdate);
    }

    // delete

    public void delete(final long id) {
        principalService.delete(id);
    }

    public void deleteAll() {
        principalService.deleteAll();
    }

    // count

    public long count() {
        return principalService.count();
    }

    // other

    public Profile getCurrentUser() {
        final Principal principal = principalService.getCurrentPrincipal();
        return new Profile(principal);
    }

    // util

    final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

}
