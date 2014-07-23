package net.zzh.sec.persistence.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.sec.model.RolePermission;
import net.zzh.sec.persistence.dao.IRolePermissionJpaDAO;
import net.zzh.sec.persistence.service.IRolePermissionService;
import net.zzh.sec.util.SearchUtilSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolePermissionServiceImpl extends AbstractService<RolePermission> implements IRolePermissionService {

    @Autowired
    IRolePermissionJpaDAO dao;

    public RolePermissionServiceImpl() {
        super(RolePermission.class);
    }

    // API

    // find
/*
    public Privilege findByName(final String name) {
        return getDao().findByName(name);
    }
*/
    // Spring

    @Override
    protected final IRolePermissionJpaDAO getDao() {
        return dao;
    }

    @Override
    public Specification<RolePermission> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, RolePermission.class);
    }

    @Override
    protected JpaSpecificationExecutor<RolePermission> getSpecificationExecutor() {
        return dao;
    }

}
