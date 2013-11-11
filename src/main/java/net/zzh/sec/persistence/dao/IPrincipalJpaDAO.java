package net.zzh.sec.persistence.dao;

import net.zzh.common.persistence.service.INameSupport;
import net.zzh.sec.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPrincipalJpaDAO extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal>, INameSupport<Principal> {
    //
}
