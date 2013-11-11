package net.zzh.sec.model.dto;

import net.zzh.sec.model.Principal;

import com.google.common.base.Function;

public final class PrincipalToUserFunction implements Function<Principal, User> {

    public final User apply(final Principal principal) {
        return new User(principal);
    }

}
