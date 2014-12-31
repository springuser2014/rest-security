package net.zzh.sec.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zzh.common.util.QueryConstants;
import net.zzh.common.web.controller.AbstractController;
import net.zzh.common.web.controller.ISortingController;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.persistence.service.IRolePermissionService;
import net.zzh.sec.util.SecurityConstants.Privileges;
import net.zzh.sec.web.common.UriMappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = UriMappingConstants.PRIVILEGES)
public class PrivilegeController extends AbstractController<RolePermission> implements ISortingController<RolePermission> {

    @Autowired
    private IRolePermissionService service;

    public PrivilegeController() {
        super(RolePermission.class);
    }

    // API

    // search
/*
    @RequestMapping(params = { QueryConstants.Q_PARAM }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.ACCESS_ADMINISTRATION_PAGES)
    public List<RolePermission> searchAll(@RequestParam(QueryConstants.Q_PARAM) final String queryString) {
        return searchAllInternal(queryString);
    }

    @RequestMapping(params = { QueryConstants.Q_PARAM, QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.ACCESS_ADMINISTRATION_PAGES)
    public List<RolePermission> searchAllPaginated(@RequestParam(QueryConstants.Q_PARAM) final String queryString, @RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size) {
        return searchAllInternalPaginated(queryString, page, size);
    }
*/
    // find - all/paginated

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE, QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.ACCESS_ADMINISTRATION_PAGES)
    public List<RolePermission> findAllPaginatedAndSorted(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, @RequestParam(value = QueryConstants.SORT_BY) final String sortBy,
            @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, sortBy, sortOrder, uriBuilder, response);
    }

    @RequestMapping(params = { QueryConstants.PAGE, QueryConstants.SIZE }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.ACCESS_ADMINISTRATION_PAGES)
    public List<RolePermission> findAllPaginated(@RequestParam(value = QueryConstants.PAGE) final int page, @RequestParam(value = QueryConstants.SIZE) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findPaginatedAndSortedInternal(page, size, null, null, uriBuilder, response);
    }

    @RequestMapping(params = { QueryConstants.SORT_BY }, method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.ACCESS_ADMINISTRATION_PAGES)
    public List<RolePermission> findAllSorted(@RequestParam(value = QueryConstants.SORT_BY) final String sortBy, @RequestParam(value = QueryConstants.SORT_ORDER) final String sortOrder) {
        return findAllSortedInternal(sortBy, sortOrder);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.ACCESS_ADMINISTRATION_PAGES)
    public List<RolePermission> findAll(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findAllInternal(request, uriBuilder, response);
    }

    // find - one

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Secured(Privileges.ACCESS_ADMINISTRATION_PAGES)
    public RolePermission findOne(@PathVariable("id") final Integer id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return findOneInternal(id, uriBuilder, response);
    }

    // create

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(Privileges.ADMINISTER_PERMISSIONS)
    public void create(@RequestBody final RolePermission resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        createInternal(resource, uriBuilder, response);
    }

    // update

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Secured(Privileges.ADMINISTER_PERMISSIONS)
    public void update(@PathVariable("id") final Integer id, @RequestBody final RolePermission resource) {
        updateInternal(id, resource);
    }

    // delete

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured(Privileges.ADMINISTER_PERMISSIONS)
    public void delete(@PathVariable("id") final Integer id) {
        deleteByIdInternal(id);
    }

    // Spring

    @Override
    protected final IRolePermissionService getService() {
        return service;
    }

}
