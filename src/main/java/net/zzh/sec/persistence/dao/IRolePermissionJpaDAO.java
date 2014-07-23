package net.zzh.sec.persistence.dao;

import net.zzh.common.persistence.service.INameSupport;
import net.zzh.sec.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRolePermissionJpaDAO extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission>, INameSupport<RolePermission> {
    //
}
