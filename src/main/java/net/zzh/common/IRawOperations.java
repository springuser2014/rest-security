package net.zzh.common;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import net.zzh.common.search.ClientOperation;

public interface IRawOperations<T> {

    // find - one

    T findOne(final int id);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAll();

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllSorted(final String sortBy, final String sortOrder);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginated(final int page, final int size);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

    // create

    T create(final T resource);

    // update

    void update(final T resource);

    // exists
    boolean exists(final int id);
    
    // delete

    void delete(final int id);

    void deleteAll();

    // count

    long count();

    // search

}
