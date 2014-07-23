package net.zzh.sec.model.dto;

import net.zzh.sec.model.User;

import com.google.common.base.Function;

public final class UserToProfileFunction implements Function<User, Profile> {

    public final Profile apply(final User user) {
        return new Profile(user);
    }

}
