/**
 * 
 */
package net.zzh.sec.persistence.dao;

import net.zzh.common.persistence.service.INameSupport;
import net.zzh.sec.model.Proc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhenhuazhao
 *
 */
public interface IProcJpaDAO extends JpaRepository<Proc, Long>, JpaSpecificationExecutor<Proc>, INameSupport<Proc> {

}
