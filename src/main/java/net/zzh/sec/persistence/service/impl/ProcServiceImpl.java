/**
 * 
 */
package net.zzh.sec.persistence.service.impl;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.sec.model.Proc;
import net.zzh.sec.persistence.dao.IProcJpaDAO;
import net.zzh.sec.persistence.service.IProcService;
import net.zzh.sec.util.SearchUtilSec;

/**
 * @author zhenhuazhao
 *
 */
@Service
@Transactional
public class ProcServiceImpl extends AbstractService<Proc> implements IProcService {

    @Autowired
    IProcJpaDAO dao;
    
	public ProcServiceImpl() {
		super(Proc.class);
		// TODO Auto-generated constructor stub
	}

    // API

    // find
/*
	@Override
	public Proc findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
*/
    // Spring

    @Override
    protected final IProcJpaDAO getDao() {
        return dao;
    }

    @Override
    public Specification<Proc> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Proc.class);
    }

    @Override
    protected JpaSpecificationExecutor<Proc> getSpecificationExecutor() {
        return dao;
    }


}
