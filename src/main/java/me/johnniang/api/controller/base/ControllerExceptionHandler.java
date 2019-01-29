package me.johnniang.api.controller.base;

import me.johnniang.api.config.properties.ApiProperties;
import me.johnniang.api.exception.BaseApiException;
import me.johnniang.api.logging.Logger;
import me.johnniang.api.support.ErrorDetail;
import me.johnniang.api.util.ValidationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Map;

/**
 * Global exception handler
 *
 * @author johnniang
 * @date 11/27/18
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger log = Logger.getLogger(getClass());

    private final ApiProperties properties;

    public ControllerExceptionHandler(ApiProperties properties) {
        this.properties = properties;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorDetail errorDetail = handleBaseException(e);
        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            errorDetail = handleBaseException(e.getCause());
        }
        errorDetail.setMessage("请求参数验证失败");
        return errorDetail;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorDetail errorDetail = handleBaseException(e);
        errorDetail.setMessage(String.format("请求方法 [%s] 不受支持, 当前仅支持 %s", e.getMethod(), e.getSupportedHttpMethods().toString()));
        return errorDetail;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        ErrorDetail errorDetail = handleBaseException(e);
        errorDetail.setMessage(String.format("请求参数错误，缺失 %s 类型的 %s 参数", e.getParameterType(), e.getParameterName()));
        return errorDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleConstraintViolationException(ConstraintViolationException e) {
        ErrorDetail errorDetail = handleBaseException(e);
        errorDetail.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorDetail.setMessage("字段验证失败");
        errorDetail.setData(e.getConstraintViolations());
        return errorDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorDetail errorDetail = handleBaseException(e);
        errorDetail.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorDetail.setMessage("字段验证失败");
        Map<String, String> errMap = ValidationUtils.mapWithFieldError(e.getBindingResult().getFieldErrors());
        errorDetail.setData(errMap);
        return errorDetail;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorDetail handleNoHandlerFoundException(NoHandlerFoundException e) {
        ErrorDetail errorDetail = handleBaseException(e);
        errorDetail.setCode(String.valueOf(HttpStatus.BAD_GATEWAY.value()));
        errorDetail.setMessage("网关无法处理此请求");
        return errorDetail;
    }

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<ErrorDetail> handleApiException(BaseApiException e) {
        ErrorDetail errorDetail = handleBaseException(e);
        errorDetail.setCode(String.valueOf(e.getStatus().value()));
        errorDetail.setData(e.getData());
        return new ResponseEntity<>(errorDetail, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetail handleGlobalException(Exception e) {
        ErrorDetail errorDetail = handleBaseException(e);
        errorDetail.setMessage("Internal server error");
        return errorDetail;
    }

    private ErrorDetail handleBaseException(Throwable t) {
        Assert.notNull(t, "throwable must not be null");

        log.error("Captured an exception", t);

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setMessage(t.getMessage());

        if (!properties.isProductionEnv()) {
            // if it's not a production env
            errorDetail.setDevMessage(ExceptionUtils.getStackTrace(t));
        }

        return errorDetail;
    }
}
