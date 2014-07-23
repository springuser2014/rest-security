package net.zzh.sec.persistence.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.security.SpringSecurityUtil;
import net.zzh.sec.model.User;
import net.zzh.sec.persistence.dao.IUserJpaDAO;
import net.zzh.sec.persistence.service.IUserService;
import net.zzh.sec.util.SearchUtilSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl extends AbstractService<User> implements IUserService {

    @Autowired
    IUserJpaDAO dao;

    public UsersServiceImpl() {
        super(User.class);
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
    protected final IUserJpaDAO getDao() {
        return dao;
    }

    @Override
    public Specification<User> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, User.class);
    }

    @Override
    protected JpaSpecificationExecutor<User> getSpecificationExecutor() {
        return dao;
    }

}
