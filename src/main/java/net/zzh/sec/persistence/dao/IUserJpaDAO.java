/**
 * 
 */
package net.zzh.sec.persistence.dao;

import net.zzh.sec.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author zhenhuazhao
 *
 */
public interface IUserJpaDAO extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
