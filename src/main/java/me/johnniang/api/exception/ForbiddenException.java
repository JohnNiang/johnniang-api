package me.johnniang.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Forbidden exception.
 *
 * @author johnniang
 */
public class ForbiddenException extends BaseApiException {

    public ForbiddenException(String message) {
        super(message);
    }

    ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
