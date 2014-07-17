package net.zzh.sec.persistence.service.dto;

import net.zzh.common.persistence.service.IService;
import net.zzh.sec.model.dto.User;

public interface IPrincipalService extends IService<User> {

    User getCurrentUser();

}
