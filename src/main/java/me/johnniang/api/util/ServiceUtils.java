package me.johnniang.api.util;

import me.johnniang.api.entity.base.BaseEntity;
import me.johnniang.api.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utilities for service.
 *
 * @author johnniang
 */
public class ServiceUtils {

    private final static Logger LOG = Logger.getLogger(ServiceUtils.class);

    private ServiceUtils() {
    }

    /**
     * Fetch domain id only.
     *
     * @param datas data collection
     * @return empty set if data collection is null or empty
     */
    public static <ID, T extends BaseEntity<ID>> Set<ID> fetchId(final Collection<T> datas) {
        return fetchProperty(datas, BaseEntity::getId);
    }

    /**
     * Fetch id to set.
     *
     * @param datas           data collection
     * @param mappingFunction calculate the id in data list
     * @param <ID>            id type
     * @param <T>             data type
     * @return a set of id
     */
    public static <ID, T> Set<ID> fetchProperty(final Collection<T> datas, Function<T, ID> mappingFunction) {
        return CollectionUtils.isEmpty(datas) ?
                Collections.emptySet() : datas.stream().map(mappingFunction).collect(Collectors.toSet());
    }

    /**
     * Convert a list to a list map where list contains id in ids.
     *
     * @param ids             id collection
     * @param list            data list
     * @param mappingFunction calculate the id in data list
     * @param <ID>            id type
     * @param <D>             data type
     * @return a map which key is in ids and value containing in list
     */
    public static <ID, D> Map<ID, List<D>> convertToListMap(Collection<ID> ids, Collection<D> list, Function<D, ID> mappingFunction) {
        Assert.notNull(mappingFunction, "mapping function must not be null");

        if (CollectionUtils.isEmpty(ids) || CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<ID, List<D>> resultMap = new HashMap<>();

        list.forEach(data -> resultMap.computeIfAbsent(mappingFunction.apply(data), id -> new ArrayList<>()).add(data));

        ids.forEach(id -> resultMap.putIfAbsent(id, Collections.emptyList()));

        return resultMap;
    }

    /**
     * Convert to map (key from the list data)
     *
     * @param list            data list
     * @param mappingFunction calclulate the id from list data
     * @param <ID>            id type
     * @param <D>             data type
     * @return a map which key from list data and value is data
     */
    public static <ID, D> Map<ID, D> convertToMap(Collection<D> list, Function<D, ID> mappingFunction) {
        Assert.notNull(mappingFunction, "mapping function must not be null");

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<ID, D> resultMap = new HashMap<>();

        list.forEach(data -> resultMap.putIfAbsent(mappingFunction.apply(data), data));

        return resultMap;
    }

    /**
     * Converti to map (key from the list data)
     *
     * @param list          data list
     * @param keyFunction   key mapping function
     * @param valueFunction value mapping function
     * @param <ID>          id type
     * @param <D>           data type
     * @param <V>           value type
     * @return a map which key from list data and value is data
     */
    public static <ID, D, V> Map<ID, V> convertToMap(Collection<D> list, Function<D, ID> keyFunction, Function<D, V> valueFunction) {
        Assert.notNull(keyFunction, "mapping function must not be null");

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<ID, V> resultMap = new HashMap<>();

        list.forEach(data -> resultMap.putIfAbsent(keyFunction.apply(data), valueFunction.apply(data)));

        return resultMap;
    }

    /**
     * Gets an empty page.
     *
     * @param <T> page data type
     * @return an empty page
     */
    public static <T> Page<T> emptyPage() {
        return new PageImpl<>(Collections.emptyList());
    }

    /**
     * Gets an empty page from another page.
     *
     * @param page another page result must not be null
     * @param <T>  to type
     * @param <F>  from type
     * @return an empty page
     */
    public static <T, F> Page<T> emptyPage(Page<F> page) {
        Assert.notNull(page, "page result must not be null");
        return new PageImpl<>(Collections.emptyList(), page.getPageable(), page.getTotalElements());
    }

    /**
     * Gets a single page request.
     *
     * @return single page request
     */
    @NonNull
    public static Pageable singlePageRequest() {
        return PageRequest.of(0, 1);
    }

    /**
     * Gets a single page request.
     *
     * @param sort sort
     * @return single page request
     */
    @NonNull
    public static Pageable singlePageRequest(@NonNull final Sort sort) {
        return PageRequest.of(0, 1, sort);
    }

    public static boolean isEmptyPage(Page<?> page) {
        return page == null || page.isEmpty();
    }

    /**
     * Judges if the given id is empty.
     *
     * @param id id to check
     * @return true if the given id is empty, false otherwise
     */
    public static boolean isEmptyId(@Nullable Object id) {
        if (id == null) {
            return true;
        }

        if (id instanceof Number) {
            return ((Number) id).longValue() <= 0;
        }

        if (id instanceof CharSequence) {
            return StringUtils.isBlank((CharSequence) id);
        }

        LOG.error("Id: [{}] with type: [{}] is not supported now", id, id.getClass().getName());
        throw new IllegalArgumentException("Id with type " + id.getClass().getSimpleName() + " is not supported to check");
    }

    /**
     * Judges if the given id is not empty.
     *
     * @param id id to check
     * @return true if the given id is not empty, false otherwise
     */
    public static boolean isNotEmptyId(@Nullable Object id) {
        return !isEmptyId(id);
    }
}