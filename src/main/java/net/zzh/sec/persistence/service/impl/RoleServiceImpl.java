package net.zzh.sec.persistence.service.impl;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.sec.model.Role;
import net.zzh.sec.persistence.dao.IRoleJpaDAO;
import net.zzh.sec.persistence.service.IRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements IRoleService {

    @Autowired
    IRoleJpaDAO dao;

    public RoleServiceImpl() {
        super(Role.class);
    }

    // API

    // get/find
    
    // create

    // Spring

    @Override
    protected final IRoleJpaDAO getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Role> getSpecificationExecutor() {
        return dao;
    }

}
