package net.zzh.sec.model.dto;

import net.zzh.sec.model.User;

import com.google.common.base.Function;

public final class UserToProfileFunction implements Function<User, UserProfile> {

    public final UserProfile apply(final User user) {
        return new UserProfile(user);
    }

}
