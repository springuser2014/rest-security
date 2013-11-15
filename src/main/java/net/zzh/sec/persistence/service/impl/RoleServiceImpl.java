package net.zzh.sec.persistence.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.sec.model.Role;
import net.zzh.sec.persistence.dao.IRoleJpaDAO;
import net.zzh.sec.persistence.service.IRoleService;
import net.zzh.sec.util.SearchUtilSec;
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
/*
    public Role findByName(final String name) {
        return getDao().findByName(name);
    }
*/
    // create

    @Override
    public Role create(final Role entity) {
        /*
         * final long id = IdUtil.randomPositiveLong(); entity.setId( id );
         */

        /*
         * final List< Privilege > associationsTemp = Lists.newArrayList( entity.getPrivileges() ); entity.getPrivileges().clear(); for( final Privilege privilege : associationsTemp ){ entity.getPrivileges().add(
         * associationDao.findByName( privilege.getName() ) ); }
         */

        return super.create(entity);
    }

    // Spring

    @Override
    public Specification<Role> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Role.class);
    }

    @Override
    protected final IRoleJpaDAO getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Role> getSpecificationExecutor() {
        return dao;
    }

}
