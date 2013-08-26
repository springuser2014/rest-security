package net.zzh.sec.persistence.service;

import net.zzh.common.persistence.service.IService;
import net.zzh.sec.model.Principal;

public interface IPrincipalService extends IService<Principal> {

    Principal getCurrentPrincipal();

}
