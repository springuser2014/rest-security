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
import net.zzh.sec.model.Test;
import net.zzh.sec.persistence.dao.ITestJpaDAO;
import net.zzh.sec.persistence.service.ITestService;
import net.zzh.sec.util.SearchUtilSec;

/**
 * @author zhenhuazhao
 *
 */
@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test> implements ITestService {

    @Autowired
    ITestJpaDAO dao;
    
	public TestServiceImpl() {
		super(Test.class);
		// TODO Auto-generated constructor stub
	}

    // API

    // find

	@Override
	public Test findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

    // Spring

    @Override
    protected final ITestJpaDAO getDao() {
        return dao;
    }

    @Override
    public Specification<Test> resolveConstraint(final Triple<String, ClientOperation, String> constraint) {
        return SearchUtilSec.resolveConstraint(constraint, Test.class);
    }

    @Override
    protected JpaSpecificationExecutor<Test> getSpecificationExecutor() {
        return dao;
    }


}
