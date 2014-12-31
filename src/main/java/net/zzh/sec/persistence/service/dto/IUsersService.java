package net.zzh.sec.persistence.service.dto;

import net.zzh.common.persistence.service.IService;
import net.zzh.sec.model.dto.UsersProfile;

public interface IUsersService extends IService<UsersProfile> {

    UsersProfile getCurrentUser();

}
