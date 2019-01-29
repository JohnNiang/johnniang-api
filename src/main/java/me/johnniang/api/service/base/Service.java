package me.johnniang.api.service.base;

import me.johnniang.api.entity.base.BaseEntity;
import me.johnniang.api.exception.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface for common services.
 *
 * @param <T>  entity type
 * @param <ID> id type
 * @author johnniang
 */
public interface Service<T extends BaseEntity<ID>, ID> {

    // Select***************************

    /**
     * List all data.
     *
     * @return all data will be returned.
     */
    List<T> listAll();

    /**
     * List all data and sort them.
     *
     * @param sort given sort rule
     * @return sorted data will be returned
     */
    List<T> listAll(Sort sort);

    /**
     * Return a Page of entities meeting the paging restriction provided in the Pageable object.
     *
     * @param pageable page info must not be null
     * @return a page of entities
     */
    Page<T> listAll(Pageable pageable);

    /**
     * Return a Page of projection entities meeting the paging restriction provided in the Pageable object.
     *
     * @param type     projection type
     * @param pageable page info
     * @param <S>      projection type
     * @return a page of projection entities
     */
    <S> Page<S> listAll(Class<S> type, Pageable pageable);

    /**
     * List all data by projection type
     *
     * @param type projection type, must not be null
     * @param <S>  projection type generic
     * @return all projection list data
     */
    <S> List<S> listAll(Class<S> type);

    /**
     * List all data by projection type
     *
     * @param type projection type, must not be null
     * @param sort given sort rule
     * @param <S>  projection type generic
     * @return all sorted projection data
     */
    <S> List<S> listAll(Class<S> type, Sort sort);

    /**
     * List all data by id collection
     *
     * @param ids id collection
     * @return all data list will be return if there has corresponding data, returning empty list if the given id collection is empty
     */
    List<T> listAllByIds(Collection<ID> ids);

    /**
     * List all data by id collection
     *
     * @param ids  id collection
     * @param sort sort info
     * @return all data list will be return if there has corresponding data, returning empty list if the given id collection is empty
     */
    List<T> listAllByIds(Collection<ID> ids, Sort sort);

    /**
     * List all data by id collection and projection type
     *
     * @param ids  id collection
     * @param type projection type
     * @param <S>  projection type generic
     * @return all data will be return if there has corresponding data, returning empty list if the given id collection is empty
     */
    <S> List<S> listAllByIds(Collection<ID> ids, Class<S> type);

    /**
     * List all data by id set and wrap with Map
     *
     * @param ids id set
     * @return return all data wrapped by Map, returning empty map if the given id collection is empty
     */
    Map<ID, T> listAllByIdsAsMap(Collection<ID> ids);

    /**
     * List all data by id set and wrap with Map
     *
     * @param ids  id set
     * @param sort sort info
     * @return return all data wrapped by Map, returning empty map if the given id collection is empty
     */
    Map<ID, T> listAllByIdsAsMap(Collection<ID> ids, Sort sort);

    /**
     * Get data by id
     *
     * @param id given data id
     * @return optional data
     */
    Optional<T> getById(ID id);

    /**
     * Get data by id
     *
     * @param id   given data id
     * @param type projection type must not be null
     * @param <S>  projection type generice
     * @return optional projection data
     */
    <S> Optional<S> getById(ID id, Class<S> type);

    /**
     * Get nullable data by id.
     *
     * @param id data id could be empty
     * @return data or null if the data id is empty or not exists
     */
    T getNullableById(ID id);

    /**
     * Get nullable data by id.
     *
     * @param id   data id could be empty
     * @param type projection type must not be null
     * @param <S>  projection type generic
     * @return data or null if the data id is empty or not exists
     */
    <S> S getNullableById(ID id, Class<S> type);

    /**
     * Get data by id and the data must be exist.
     *
     * @param id given data id
     * @return existent data
     * @throws NotFoundException in case the corresponding data not exist.
     */
    T getNonnullById(ID id);

    /**
     * Get data by id and data must be exist.
     *
     * @param id   given data id
     * @param type projection type must not be null
     * @param <S>  projection type generic
     * @return existent data
     * @throws NotFoundException in case the corresponding data not exist.
     */
    <S> S getNonnullById(ID id, Class<S> type);

    /**
     * Check whether the data is exists or not.
     *
     * @param id data id
     * @return true: exists, or not.
     */
    boolean existsById(ID id);

    /**
     * The given id must exist.
     *
     * @param id data id
     */
    void existsByIdOfMust(ID id);


    // Create******************************

    /**
     * Create data into database.
     *
     * @param data data to be created
     * @return created data will be returned
     */
    T create(T data);

    /**
     * Create data batchly.
     *
     * @param datas data collection to be created
     * @return created data list to be returned
     */
    List<T> create(Collection<T> datas);

    // Update******************************

    /**
     * Update data into database
     *
     * @param data data to be updated
     * @return updated data will be returned
     */
    T update(T data);

    /**
     * Update data batchly.
     *
     * @param datas data collection to be updated
     * @return updated data list will be returned
     */
    List<T> update(Collection<T> datas);

    // Delete******************************

    /**
     * Remove data by given id.
     *
     * @param id given domain id, must not be null
     * @throws EmptyResultDataAccessException if the given id is not exists.
     */
    void removeById(ID id);

    /**
     * Remove data by given data creteria.
     *
     * @param data given data with creteria must not be null
     */
    void remove(T data);

    /**
     * Remove data by data collecion.
     *
     * @param datas given data collection
     */
    void removeAll(Collection<T> datas);

    /**
     * Revmoe data by id collection
     *
     * @param ids id collection
     */
    void removeInBatch(Collection<ID> ids);

}
