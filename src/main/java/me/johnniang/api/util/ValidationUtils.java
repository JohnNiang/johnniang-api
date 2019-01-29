package me.johnniang.api.util;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utilities for validation.
 *
 * @author johnniang
 */
public class ValidationUtils {

    private final static Validator VALIDATOR;

    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private ValidationUtils() {
    }

    /**
     * 将字段验证错误转换为标准的map型，key:value = field:message
     *
     * @param constraintViolations constraint violations(contain error information)
     * @return 如果返回null则未出现错误
     */
    public static Map<String, String> mapWithValidError(Set<ConstraintViolation<Object>> constraintViolations) {
        Map<String, String> errMap = null;
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            // if not empty
            errMap = new HashMap<>(4);
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                errMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
        }
        return errMap;
    }

    /**
     * 将字段验证错误转换为标准的map型，key:value = field:message
     *
     * @param fieldErrors 字段错误组
     * @return 如果返回null，则表示未出现错误
     */
    public static Map<String, String> mapWithFieldError(List<FieldError> fieldErrors) {
        Map<String, String> errMap = null;

        if (!CollectionUtils.isEmpty(fieldErrors)) {
            // 如果不为空
            errMap = new HashMap<>(4);
            for (FieldError fieldError : fieldErrors) {
                errMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }

        return errMap;
    }

    /**
     * Validates bean by hand.
     *
     * @param obj    bean to be validated
     * @param groups validation group
     * @throws ConstraintViolationException throw if validation failure
     */
    public static void validate(Object obj, Class<?>... groups) {

        // 验证实体
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(obj, groups);

        if (!CollectionUtils.isEmpty(constraintViolations)) {
            // 如果存在错误则 throw exception
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
