package net.zzh.sec.persistence.dao;

import net.zzh.common.persistence.service.INameSupport;
import net.zzh.sec.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRoleJpaDAO extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role>, INameSupport<Role> {
    //
}
