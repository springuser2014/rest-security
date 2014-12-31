package net.zzh.sec.persistence.dao;

import net.zzh.common.persistence.service.INameSupport;
import net.zzh.sec.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUsersJpaDAO extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users>, INameSupport<Users> {
    //
}
