package net.zzh.common.persistence.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zzh.common.persistence.service.IPersistenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PersistenceService 实现
 * @author zhenhuazhao
 *
 */
@Service
@Transactional
public class PersistenceServiceImpl implements IPersistenceService {
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	@Autowired(required=false)
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List findByNativeSQL(final String sql) {
		List list = em.createNativeQuery(sql).getResultList();
		em.clear();
		return list;
	}
	
	@Override
	public Page findPaginated(final int page, final int size, final String sql) {
		int totalPages = 0,
			count = em.createQuery(sql).getResultList().size();

		if(count/size<0){
			totalPages=1;
		}else{
			totalPages = ((double)count/(double)size)>(count/size) ? count/size+1 : count/size;
		}
		int start = (page>totalPages ? totalPages : page-1) * size;
		Query query = em.createQuery(sql);
		
		List resultList = query.getResultList();
		
		Pageable pageable = new PageRequest( page, size );
		
		Page p = new PageImpl(resultList, pageable, resultList.size());
		return p;
	}
	
	@Override
	public Page findNativeQueryPaginated(final int page, final int size, final String sql) {
		int totalPages = 0,
			count = em.createNativeQuery(sql).getResultList().size();

		if(count/size<0){
			totalPages=1;
		}else{
			totalPages = ((double)count/(double)size)>(count/size) ? count/size+1 : count/size;
		}
		int start = (page>totalPages ? totalPages : page-1) * size;
		Query query = em.createNativeQuery(sql);
		
		List resultList = query.getResultList();
		
		Pageable pageable = new PageRequest( page, size );
		
		Page p = new PageImpl(resultList, pageable, resultList.size());
		return p;
	}
	
	@Override
	public boolean execute(final String sql) {
		try{
			Query query = em.createNativeQuery(sql);
			query.executeUpdate();
			return true;
		}catch(Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Override
	public String executeGetResult(final String sql) {
		try{
			Query query = em.createNativeQuery(sql);
			return query.getSingleResult().toString();
		}catch(Exception e) {
			logger.error(e.getMessage());
			return "";
		}
	}
}
