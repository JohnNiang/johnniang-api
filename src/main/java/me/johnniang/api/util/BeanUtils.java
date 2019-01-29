package me.johnniang.api.util;

import me.johnniang.api.exception.BeanUtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.TypeUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utilities for bean operation.
 *
 * @author johnniang
 */
public class BeanUtils {

    private final static Logger LOG = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {
    }

    /**
     * Transform from other object collection batchly.
     *
     * @param clazz   destination clazz
     * @param sources source object collection
     * @param <T>     destination class generice type
     * @return copied destination object list
     */
    public static <T> List<T> transfromInBatch(final Class<T> clazz, Collection<?> sources) {
        return CollectionUtils.isEmpty(sources) ?
                Collections.emptyList() : sources.stream().map(source -> transfrom(clazz, source)).collect(Collectors.toList());
    }

    /**
     * Transform from other object.
     *
     * @param clazz destination clazz
     * @param src   source object
     * @param <T>   destination class generice type
     * @return copied destination object
     */
    public static <T> T transfrom(final Class<T> clazz, final Object src) {
        Assert.notNull(clazz, "destination clazz must not be null");

        if (src == null) {
            return null;
        }

        // init the instance

        try {
            T instance = clazz.newInstance();

            String[] nullPropertyNames = getNullPropertyNames(src);

            // really copy
            BeanUtilsImpl.copyProperties(src, instance, nullPropertyNames);

            // return instance
            return instance;
        } catch (Exception e) {
            throw new BeanUtilsException("new instance or copy properties error", e);
        }
    }

    /**
     * Update target object from source object.
     *
     * @param source source object
     * @param target target object
     */
    public static void update(final Object source, final Object target) {
        Assert.notNull(source, "source object must not be null");
        Assert.notNull(target, "target object must not be null");

        try {
            BeanUtilsImpl.copyProperties(source, target, getNullPropertyNames(source));
        } catch (Exception e) {
            throw new BeanUtilsException("copy properties error", e);
        }
    }

    private static final String[] COPY_IGNORED_PROPERTIES = {"id", "createTime", "updateTime", "deleted"};

    /**
     * Get null property names
     *
     * @param src object to be checked
     * @return return null property name array
     */
    private static String[] getNullPropertyNames(final Object src) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(src);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            // if propertye value is equal to null, add it to empty name set
            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }

        return emptyNames.toArray(new String[0]);
    }

    public static class ClassUtilsImpl extends ClassUtils {

        /**
         * Map with primitive wrapper type as key and corresponding primitive
         * type as value, for example: Integer.class -> int.class.
         */
        private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

        /**
         * Map with primitive type as key and corresponding wrapper
         * type as value, for example: int.class -> Integer.class.
         */
        private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

        static {
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
            PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);

            // Map entry iteration is less expensive to initialize than forEach with lambdas
            for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
                PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
            }
        }

        /**
         * Check if the right-hand side type may be assigned to the left-hand side
         * type, assuming setting by reflection. Considers primitive wrapper
         * classes as assignable to the corresponding primitive types.
         *
         * @param lhsType the target type
         * @param rhsType the value type that should be assigned to the target type
         * @return if the target type is assignable from the value type
         * @see TypeUtils#isAssignable
         */
        public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
            Assert.notNull(lhsType, "Left-hand side type must not be null");
            Assert.notNull(rhsType, "Right-hand side type must not be null");

            // handle generice.
            if (rhsType.equals(Object.class)) {
                return true;
            }

            return ClassUtils.isAssignable(lhsType, rhsType);
        }
    }

    public static class BeanUtilsImpl extends org.springframework.beans.BeanUtils {

        private BeanUtilsImpl() {
        }


        /**
         * Copy the property values of the given source bean into the target bean.
         * <p>Note: The source and target classes do not have to match or even be derived
         * from each other, as long as the properties match. Any bean properties that the
         * source bean exposes but the target bean does not will silently be ignored.
         * <p>This is just a convenience method. For more complex transfer needs,
         * consider using a full BeanWrapper.
         *
         * @param source the source bean
         * @param target the target bean
         * @throws BeansException if the copying failed
         * @see BeanWrapper
         */
        public static void copyProperties(Object source, Object target) throws BeansException {
            copyProperties(source, target, null, (String[]) null);
        }

        /**
         * Copy the property values of the given source bean into the given target bean,
         * only setting properties defined in the given "editable" class (or interface).
         * <p>Note: The source and target classes do not have to match or even be derived
         * from each other, as long as the properties match. Any bean properties that the
         * source bean exposes but the target bean does not will silently be ignored.
         * <p>This is just a convenience method. For more complex transfer needs,
         * consider using a full BeanWrapper.
         *
         * @param source   the source bean
         * @param target   the target bean
         * @param editable the class (or interface) to restrict property setting to
         * @throws BeansException if the copying failed
         * @see BeanWrapper
         */
        public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
            copyProperties(source, target, editable, (String[]) null);
        }

        /**
         * Copy the property values of the given source bean into the given target bean,
         * ignoring the given "ignoreProperties".
         * <p>Note: The source and target classes do not have to match or even be derived
         * from each other, as long as the properties match. Any bean properties that the
         * source bean exposes but the target bean does not will silently be ignored.
         * <p>This is just a convenience method. For more complex transfer needs,
         * consider using a full BeanWrapper.
         *
         * @param source           the source bean
         * @param target           the target bean
         * @param ignoreProperties array of property names to ignore
         * @throws BeansException if the copying failed
         * @see BeanWrapper
         */
        public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
            copyProperties(source, target, null, ignoreProperties);
        }

        /**
         * Copy the property values of the given source bean into the given target bean.
         * <p>Note: The source and target classes do not have to match or even be derived
         * from each other, as long as the properties match. Any bean properties that the
         * source bean exposes but the target bean does not will silently be ignored.
         *
         * @param source           the source bean
         * @param target           the target bean
         * @param editable         the class (or interface) to restrict property setting to
         * @param ignoreProperties array of property names to ignore
         * @throws BeansException if the copying failed
         * @see BeanWrapper
         */
        private static void copyProperties(Object source, Object target, @Nullable Class<?> editable,
                                           @Nullable String... ignoreProperties) throws BeansException {

            Assert.notNull(source, "Source must not be null");
            Assert.notNull(target, "Target must not be null");

            Class<?> actualEditable = target.getClass();
            if (editable != null) {
                if (!editable.isInstance(target)) {
                    throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                            "] not assignable to Editable class [" + editable.getName() + "]");
                }
                actualEditable = editable;
            }
            PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
            List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

            for (PropertyDescriptor targetPd : targetPds) {
                Method writeMethod = targetPd.getWriteMethod();
                if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                    PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                    if (sourcePd != null) {
                        Method readMethod = sourcePd.getReadMethod();
                        if (readMethod != null &&
                                ClassUtilsImpl.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                            try {
                                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                    readMethod.setAccessible(true);
                                }
                                Object value = readMethod.invoke(source);
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            } catch (Throwable ex) {
                                throw new FatalBeanException(
                                        "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                            }
                        }
                    }
                }
            }
        }
    }
}
