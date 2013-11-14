/**
 * 
 */
package net.zzh.sec.persistence.dao;

import net.zzh.common.persistence.service.INameSupport;
import net.zzh.sec.model.Test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhenhuazhao
 *
 */
public interface ITestJpaDAO extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test>, INameSupport<Test> {

}
