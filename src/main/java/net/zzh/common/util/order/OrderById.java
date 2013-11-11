package net.zzh.common.util.order;

import net.zzh.common.persistence.model.IEntity;

import com.google.common.collect.Ordering;

public final class OrderById<T extends IEntity> extends Ordering<T> {

    public OrderById() {
        super();
    }

    // API

    @Override
    public final int compare(final T left, final T right) {
        return left.getId().compareTo(right.getId());
    }

}
