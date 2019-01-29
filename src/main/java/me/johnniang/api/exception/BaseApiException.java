package me.johnniang.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Global exception
 *
 * @author johnniang
 */
public abstract class BaseApiException extends RuntimeException {

    /**
     * Error data.
     */
    private Object data;

    BaseApiException(String message) {
        super(message);
    }

    BaseApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * get exception status
     *
     * @return status should be returned.
     */
    public abstract HttpStatus getStatus();

    public Object getData() {
        return data;
    }

    public BaseApiException setData(Object data) {
        this.data = data;
        return this;
    }
}
