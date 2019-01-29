package me.johnniang.api.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Base repository for convenience.
 *
 * @param <T>  domain type
 * @param <ID> domain id type
 * @author johnniang
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * Find all data by projection type.
     *
     * @param type given projection type must not be null
     * @param <S>  projection generic type
     * @return all data of projection type will be returned
     */
    <S> List<S> findAllBy(Class<S> type);

    /**
     * Find all data by projection type and sort.
     *
     * @param type projection type must not be null
     * @param sort given sort rule
     * @param <S>  projection generice type
     * @return all sorted data of projectoin type will be returned
     */
    <S> List<S> findAllBy(Class<S> type, Sort sort);

    /**
     * Return a Page of projection entities meeting the paging restriction provided in the Pageable object.
     *
     * @param type     projection type
     * @param pageable page info must not be null
     * @param <S>      projection type
     * @return a page of projection entities
     */
    <S> Page<S> findAllBy(Class<S> type, Pageable pageable);

    /**
     * Find all data by id collection and projectoin type.
     *
     * @param ids  id collection
     * @param type projecton type
     * @param <S>  projection generic type
     * @return a list which data's id in ids
     */
    <S> List<S> findAllByIdIn(Iterable<ID> ids, Class<S> type);

    /**
     * Find all data by id collection and sort info
     *
     * @param ids  id collection
     * @param sort sort info
     * @return a list of data
     */
    List<T> findAllByIdIn(Iterable<ID> ids, Sort sort);

    /**
     * Find data by id with optional.
     *
     * @param id   id must not be null
     * @param type projection type must not be null
     * @param <S>  projection generic type
     * @return an optional data will be returned
     */
    <S> Optional<S> findById(ID id, Class<S> type);

    /**
     * Delete by id collection
     *
     * @param ids id collection
     */
    void deleteByIdIn(Iterable<ID> ids);
}