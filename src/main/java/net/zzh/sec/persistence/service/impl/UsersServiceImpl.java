package net.zzh.sec.persistence.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.tuple.Triple;

import net.zzh.common.persistence.service.AbstractService;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.security.SpringSecurityUtil;
import net.zzh.sec.model.Users;
import net.zzh.sec.model.Users_;
import net.zzh.sec.persistence.dao.IUsersJpaDAO;
import net.zzh.sec.persistence.service.IUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl extends AbstractService<Users> implements IUsersService {

	@Autowired
	IUsersJpaDAO dao;

	public UsersServiceImpl() {
		super(Users.class);
	}

	// API

	// find

	// other

	@Transactional(readOnly = true)
	public Users getCurrentPrincipal() {
		final String userName = SpringSecurityUtil.getNameOfCurrentPrincipal();

		return getDao().findOne(this.hasName(userName));
	}

	private Specification<Users> hasName(final String userName) {
		return new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(Users_.name), userName);
			}
		};
	}

	// Spring

	@Override
	protected final IUsersJpaDAO getDao() {
		return dao;
	}

	@Override
	protected JpaSpecificationExecutor<Users> getSpecificationExecutor() {
		return dao;
	}

}
