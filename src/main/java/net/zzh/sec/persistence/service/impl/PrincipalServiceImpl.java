package net.zzh.sec.persistence.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.security.SpringSecurityUtil;
import net.zzh.sec.model.Principal;
import net.zzh.sec.persistence.dao.IPrincipalJpaDAO;
import net.zzh.sec.persistence.service.IPrincipalService;
import net.zzh.sec.util.SearchUtilSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrincipalServiceImpl extends AbstractService<Principal> implements IPrincipalService {

    @Autowired
    IPrincipalJpaDAO dao;

    public PrincipalServiceImpl() {
        super(Principal.class);
    }

    // API

    // find
/*
    @Transactional(readOnly = true)
    public Principal findByName(final String name) {
        return dao.findByName(name);
    }
*/
    // other
/*
    @Transactional(readOnly = true)
    public Principal getCurrentPrincipal() {
        final String principalName = SpringSecurityUtil.getNameOfCurrentPrincipal();
        return getDao().findByName(principalName);
    }
*/
    // Spring

    @Override
    protected final IPrincipalJpaDAO getDao() {
        return dao;
    }

    @Override
    public Specification<Principal> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Principal.class);
    }

    @Override
    protected JpaSpecificationExecutor<Principal> getSpecificationExecutor() {
        return dao;
    }

}
