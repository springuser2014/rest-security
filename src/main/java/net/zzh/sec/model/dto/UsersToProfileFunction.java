package net.zzh.sec.model.dto;

import net.zzh.sec.model.Users;

import com.google.common.base.Function;

public final class UsersToProfileFunction implements Function<Users, UsersProfile> {

    public final UsersProfile apply(final Users user) {
        return new UsersProfile(user);
    }

}
