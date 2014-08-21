package net.zzh.common.persistence.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.data.domain.Page;

public interface IPersistenceService{
	
	public CriteriaBuilder getCriteriaBuilder();
	
	/**
	 * 查询全表
	 * @param sql
	 * @return
	 */
	public List findByNativeSQL(final String sql);
	/**
	 * 分页
	 * @param page 请求页
	 * @param size 每页行数
	 * @param sql 查询语句
	 * @return
	 */
	public Page findPaginated( final int page, final int size, final String sql);
	/**
	 * 
	 * @param page 请求页
	 * @param size 每页行数
	 * @param sql 查询语句
	 * @return
	 */
	public Page findNativeQueryPaginated(int page, int size, String sql);
	/**
	 * 执行语句
	 * @param sql
	 * @return
	 */
	public boolean execute(String sql);
	/**
	 * 调用返回单值的存储过程
	 * @param sql
	 * @return
	 */
	public String executeGetResult(String sql);
}
