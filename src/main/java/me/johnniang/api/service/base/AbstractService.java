package me.johnniang.api.service.base;

import me.johnniang.api.entity.base.BaseEntity;
import me.johnniang.api.exception.NotFoundException;
import me.johnniang.api.logging.Logger;
import me.johnniang.api.repository.base.BaseRepository;
import me.johnniang.api.util.ServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Abstract service implementation.
 *
 * @param <T>  entity type
 * @param <ID> id type
 */
public abstract class AbstractService<T extends BaseEntity<ID>, ID> implements Service<T, ID> {

    protected final Logger logger = Logger.getLogger(getClass());

    private final static String SERVICE_SUFFIX = "ServiceImpl";

    private final BaseRepository<T, ID> repository;

    private final String prefixClassName;

    protected AbstractService(BaseRepository<T, ID> repository) {
        this.repository = repository;
        prefixClassName = StringUtils.removeEnd(getClass().getSimpleName(), SERVICE_SUFFIX).toLowerCase();
    }

    @Override
    public List<T> listAll() {
        return repository.findAll();
    }

    @Override
    public List<T> listAll(Sort sort) {
        Assert.notNull(sort, prefixClassName + " sort must not be null");

        return repository.findAll(sort);
    }

    @Override
    public Page<T> listAll(Pageable pageable) {
        Assert.notNull(pageable, prefixClassName + " pageable must not be null");

        return repository.findAll(pageable);
    }

    @Override
    public <S> Page<S> listAll(Class<S> type, Pageable pageable) {
        Assert.notNull(type, prefixClassName + " projection type must not be null");
        Assert.notNull(pageable, prefixClassName + " pageable must not be null");

        return repository.findAllBy(type, pageable);
    }

    @Override
    public <S> List<S> listAll(Class<S> type, Sort sort) {
        Assert.notNull(type, prefixClassName + " type must not be null");
        Assert.notNull(sort, prefixClassName + " sort must not be null");

        return repository.findAllBy(type, sort);
    }

    @Override
    public <S> List<S> listAll(Class<S> type) {
        Assert.notNull(type, prefixClassName + " type must not be null");

        return repository.findAllBy(type);
    }

    @Override
    public List<T> listAllByIds(Collection<ID> ids) {
        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : repository.findAllById(ids);
    }

    @Override
    public List<T> listAllByIds(Collection<ID> ids, Sort sort) {
        return CollectionUtils.isEmpty(ids) ?
                Collections.emptyList() : repository.findAllByIdIn(ids, sort);
    }

    @Override
    public <S> List<S> listAllByIds(Collection<ID> ids, Class<S> type) {
        Assert.notNull(type, prefixClassName + " type must not be null");

        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : repository.findAllByIdIn(ids, type);
    }

    @Override
    public Map<ID, T> listAllByIdsAsMap(Collection<ID> ids) {
        return ServiceUtils.convertToMap(listAllByIds(ids), BaseEntity::getId);
    }

    @Override
    public Map<ID, T> listAllByIdsAsMap(Collection<ID> ids, Sort sort) {
        return ServiceUtils.convertToMap(listAllByIds(ids, sort), BaseEntity::getId);
    }

    @Override
    public Optional<T> getById(ID id) {
        Assert.isTrue(ServiceUtils.isNotEmptyId(id), prefixClassName + " id must not be null");

        return repository.findById(id);
    }

    @Override
    public <S> Optional<S> getById(ID id, Class<S> type) {
        Assert.isTrue(ServiceUtils.isNotEmptyId(id), prefixClassName + " id must not be empty");
        Assert.notNull(type, prefixClassName + " projection type must not be null");

        return repository.findById(id, type);
    }

    @Override
    public T getNullableById(ID id) {
        if (ServiceUtils.isEmptyId(id)) {
            return null;
        }

        return getById(id).orElse(null);
    }

    @Override
    public <S> S getNullableById(ID id, Class<S> type) {
        if (ServiceUtils.isEmptyId(id)) {
            return null;
        }
        return getById(id, type).orElse(null);
    }

    @Override
    public T getNonnullById(ID id) {
        return getById(id).orElseThrow(() -> new NotFoundException(String.format("data with %s id [%s] was not found", prefixClassName, id)));
    }

    @Override
    public <S> S getNonnullById(ID id, Class<S> type) {
        return getById(id, type).orElseThrow(() -> new NotFoundException(String.format("data with %s id [%s] was not found", prefixClassName, id)));
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public void existsByIdOfMust(ID id) {
        if (!existsById(id)) {
            throw new NotFoundException(String.format("data with %s id [%s] was not found", prefixClassName, id));
        }
    }

    @Override
    public T create(T data) {
        Assert.notNull(data, prefixClassName + " data to be created must not be null");
        return repository.save(data);
    }

    @Override
    public List<T> create(Collection<T> datas) {
        return CollectionUtils.isEmpty(datas) ? Collections.emptyList() : repository.saveAll(datas);
    }

    @Override
    public T update(T data) {
        Assert.notNull(data, prefixClassName + " data to be updated must not be null");

        return repository.saveAndFlush(data);
    }

    @Override
    public List<T> update(Collection<T> datas) {
        return CollectionUtils.isEmpty(datas) ? Collections.emptyList() : repository.saveAll(datas);
    }

    @Override
    public void removeById(ID id) {
        T data = getNonnullById(id);

        logger.debug("check {} data: [{}]", prefixClassName, data);

        remove(data);
    }

    @Override
    public void remove(T data) {
        Assert.notNull(data, prefixClassName + " data to be remoed must not be null");

        repository.delete(data);
    }

    @Override
    public void removeAll(Collection<T> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }

        repository.deleteInBatch(datas);
    }

    @Override
    public void removeInBatch(Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        repository.deleteByIdIn(ids);
    }

}
