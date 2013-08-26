package net.zzh.sec.persistence.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.sec.model.Privilege;
import net.zzh.sec.persistence.dao.IPrivilegeJpaDAO;
import net.zzh.sec.persistence.service.IPrivilegeService;
import net.zzh.sec.util.SearchUtilSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege> implements IPrivilegeService {

    @Autowired
    IPrivilegeJpaDAO dao;

    public PrivilegeServiceImpl() {
        super(Privilege.class);
    }

    // API

    // find

    public Privilege findByName(final String name) {
        return getDao().findByName(name);
    }

    // Spring

    @Override
    protected final IPrivilegeJpaDAO getDao() {
        return dao;
    }

    @Override
    public Specification<Privilege> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Privilege.class);
    }

    @Override
    protected JpaSpecificationExecutor<Privilege> getSpecificationExecutor() {
        return dao;
    }

}
