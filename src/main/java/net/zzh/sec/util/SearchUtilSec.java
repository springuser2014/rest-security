package net.zzh.sec.util;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.persistence.model.IEntity;
import net.zzh.common.search.ClientOperation;
import net.zzh.common.util.QueryConstants;
import org.springframework.data.jpa.domain.Specification;

public final class SearchUtilSec {

    private SearchUtilSec() {
        throw new UnsupportedOperationException();
    }

    // util

    public static <T extends IEntity> Specification<T> resolveConstraint(final Triple<String, ClientOperation, String> constraint, final Class<T> clazz) {
        final String constraintName = constraint.getLeft();
        final boolean negated = isConstraintNegated(constraint);

        if (constraintName.equals(QueryConstants.NAME)) {
            return QuerySpecificationSec.getByNameSpecification(clazz, constraint.getMiddle(), constraint.getRight(), negated);
        }
        if (constraintName.equals(QueryConstants.ID)) {
            return QuerySpecificationSec.getByIdSpecification(clazz, Long.parseLong(constraint.getRight()), negated);
        }
        return null;
    }

    static boolean isConstraintNegated(final Triple<String, ClientOperation, String> constraint) {
        return constraint.getMiddle().isNegated();
    }

}
