package me.johnniang.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Enity exists exception.
 *
 * @author johnniang
 */
public class EntityExistsException extends BaseApiException {

    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
